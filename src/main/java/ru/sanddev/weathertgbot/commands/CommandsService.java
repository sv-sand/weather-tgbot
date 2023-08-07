package ru.sanddev.weathertgbot.commands;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.impl.UndefinedCommand;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 11.06.2023
 */

@Log4j
@Service
public class CommandsService {

    @Getter
    private Map<BotChat, Command> activeCommands;

    private Map<String, String> commandCollection;

    public CommandsService() {
        activeCommands = new HashMap<>();
        initCommandCollection();
    }

    private void initCommandCollection() {
        commandCollection = new HashMap<>();
        commandCollection.put("/start", "StartCommand");
        commandCollection.put("/help", "HelpCommand");
        commandCollection.put("/break", "BreakCommand");
        commandCollection.put("/lang", "LangCommand");
        commandCollection.put("/city", "CityCommand");
        commandCollection.put("/weather", "WeatherCommand");
    }

    public void processCommand(BotChat chat, String messageText) {

        String commandClassName = commandCollection.get(messageText);
        if (commandClassName != null) {
            log.debug(messageText + " command recognized");
            Command command = createCommand(commandClassName, chat);
            command.process();
            return;
        }

        if (isAnswerExpecting(chat)) {
            processAnswer(chat, messageText);
            return;
        }

        log.error("Command not recognized");
        Command command = new UndefinedCommand(chat);
        command.process();
    }

    private Command createCommand(String className, BotChat chat) {
        Command command = new UndefinedCommand(chat);

        try {
            command = newCommand(className, chat);
        } catch (ClassNotFoundException | NoSuchMethodException| InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error(e.getLocalizedMessage());
        }

        return command;
    }

    private Command newCommand(String className, BotChat chat) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String classPath = "ru.sanddev.weathertgbot.commands.impl.";

        Class<?> clazz = Class.forName(classPath + className);
        Constructor constructor = clazz.getConstructor(chat.getClass());
        Command command = (Command) constructor.newInstance(chat);

        return command;
    }

    private boolean isAnswerExpecting(BotChat chat) {
        return activeCommands.containsKey(chat);
    }

    private boolean processAnswer(BotChat chat, String messageText) {
        Command command = activeCommands.get(chat);
        log.error(String.format("Answer by command %s recognized", command.getClass().toString()));

        command.processAnswer(messageText);
        return true;
    }
}
