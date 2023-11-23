package ru.sanddev.weathertgbot.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.sanddev.weathertgbot.bot.Bot;
import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.keyboards.InlineKeyboard;
import ru.sanddev.weathertgbot.bot.keyboards.ReplyKeyboard;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This service manage the process of run commands
 * @author sand <sve.snd@gmail.com>
 * @since 11.06.2023
 */

@Log4j
@Service
public class CommandsService {

    @Getter @Setter
    private Bot bot;

    @Getter
    private final Map<Chat, Command> commandsAwaitingResponse = new HashMap<>();

    @Getter
    private CommandManager commandManager = new CommandManager();

    public void init(Bot bot) {
        this.bot = bot;
    }

    public void processMessageText(Chat chat, String messageText) {

        CommandRecognizer recognizer = new CommandRecognizer(commandManager.getAllCommands());
        recognizer.recognize(messageText);
        if (recognizer.isCommand()) {
            processCommand(chat, recognizer);
            return;
        }

        if (isAnswerExpecting(chat)) {
            processAnswer(chat, messageText);
            return;
        }

        sendCommandNotRecognized(chat);
    }

    private void processCommand(Chat chat, CommandRecognizer recognizer) {
        log.debug(recognizer.getId() + " command recognized");

        if (isAllowedCommand(chat, recognizer.getId()))
            startNewCommand(recognizer.getClazz(), chat, recognizer.getParams());
        else
            sendCommandRestricted(chat);
    }

    private boolean isAllowedCommand(Chat chat, String id) {
        Class commandClass;

        if (chat.getUser().isAdmin())
            commandClass = commandManager.getAdminCommands().get(id);
        else
            commandClass = commandManager.getUserCommands().get(id);

        return commandClass != null;
    }

    private void startNewCommand(Class clazz, Chat chat, List<String> params) {
        Command command = newCommand(clazz, chat);
        commandsAwaitingResponse.remove(chat);

        if (command.hasParams())
            command.setParams(params);

        command.process();
    }

    public Command newCommand(Class clazz, Chat chat) {
        Command command;
        try {
            Constructor constructor = clazz.getConstructor(CommandsService.class, chat.getClass());
            command = (Command) constructor.newInstance(this, chat);
        } catch (
                NoSuchMethodException | SecurityException| InstantiationException |
                IllegalAccessException | InvocationTargetException |IllegalArgumentException e
        ) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        return command;
    }

    public boolean isAnswerExpecting(Chat chat) {
        return commandsAwaitingResponse.containsKey(chat);
    }

    private void processAnswer(Chat chat, String messageText) {
        Command command = commandsAwaitingResponse.get(chat);
        log.info(String.format("Answer by command %s recognized", command.getClass().toString()));
        command.processAnswer(messageText);
    }

    public void send(Chat chat, String text) {
        bot.send(chat, text);
    }

    public void send(Chat chat, String text, InlineKeyboard keyboard) {
        bot.send(chat, text, keyboard.getKeyboard());
    }

    public void send(Chat chat, String text, ReplyKeyboard keyboard) {
        bot.send(chat, text, keyboard.getKeyboard());
    }

    private void sendCommandNotRecognized(Chat chat) {
        log.error("Command not recognized");
        send(chat, chat.getDialog("command_not_recognized"));
    }

    private void sendCommandRestricted(Chat chat) {
        log.error("Command restricted");
        send(chat, chat.getDialog("command_restricted"));
    }
}
