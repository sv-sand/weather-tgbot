package ru.sanddev.weathertgbot.bot.commands;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.bot.BotChat;
import ru.sanddev.weathertgbot.bot.commands.impl.*;

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
    private final Map<BotChat, Command> commandsAwaitingResponse;
    private final Map<String, String> commandCollection;

    public CommandsService() {
        this.commandsAwaitingResponse = new HashMap<>();

        commandCollection = new HashMap<>();
        commandCollection.put(StartCommand.ID, StartCommand.class.getName());
        commandCollection.put(HelpCommand.ID, HelpCommand.class.getName());
        commandCollection.put(BreakCommand.ID, BreakCommand.class.getName());
        commandCollection.put(LangCommand.ID, LangCommand.class.getName());
        commandCollection.put(CityCommand.ID, CityCommand.class.getName());
        commandCollection.put(WeatherCommand.ID, WeatherCommand.class.getName());
        commandCollection.put(NotificationCommand.ID, NotificationCommand.class.getName());
    }

    // Incoming messages

    public void processCommand(BotChat chat, String messageText) {

        String commandClassName = commandCollection.get(messageText);
        if (commandClassName != null) {
            startNewCommand(commandClassName, chat);
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

    private void startNewCommand(String className, BotChat chat) {

        Command command;
        try {
            command = newCommand(className, chat);
        } catch (ClassNotFoundException | NoSuchMethodException| InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error(e.getLocalizedMessage());
            return;
        }

        log.debug(className + " command recognized");
        commandsAwaitingResponse.remove(chat);

        command.process();
    }

    private Command newCommand(String className, BotChat chat) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(className);
        Constructor constructor = clazz.getConstructor(chat.getClass());

        return (Command) constructor.newInstance(chat);
    }

    public void processAnswer(BotChat chat, String messageText) {

        if (!isAnswerExpecting(chat)) {
            Command command = new OutOfDateCommand(chat);
            log.error("Answer out of date recognized");
            command.process();
        }
        else {
            Command command = commandsAwaitingResponse.get(chat);
            log.error(String.format("Answer by command %s recognized", command.getClass().toString()));
            command.processAnswer(messageText);
        }
    }

    public boolean isAnswerExpecting(BotChat chat) {
        return commandsAwaitingResponse.containsKey(chat);
    }

    // Outgoing messages

    protected void sendMessage(BotChat chat, String text) {
        SendMessage message = new SendMessage(chat.getUser().getId(), text);
        message.setParseMode(ParseMode.HTML);

        AppWeatherBot.getContext().getBotMessageSender()
                .send(message);
    }

    protected void sendMessage(BotChat chat, String text, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage(chat.getUser().getId(), text);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(keyboard);

        AppWeatherBot.getContext().getBotMessageSender()
                .send(message);
    }
}
