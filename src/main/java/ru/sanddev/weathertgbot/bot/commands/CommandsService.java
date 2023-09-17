package ru.sanddev.weathertgbot.bot.commands;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.bot.BotMenu;
import ru.sanddev.weathertgbot.bot.TgChat;
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
    private final Map<TgChat, Command> commandsAwaitingResponse;
    private final Map<String, Class<? extends BaseCommand>> availableCommands;

    public CommandsService() {
        commandsAwaitingResponse = new HashMap<>();
        availableCommands = new BotMenu()
                .getAvailableCommands();
    }

    // Incoming messages

    public void processCommand(TgChat chat, String messageText) {

        Class commandClass = availableCommands.get(messageText);
        if (commandClass != null) {
            startNewCommand(commandClass, chat);
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

    private void startNewCommand(Class clazz, TgChat chat) {

        Command command;
        try {
            Constructor constructor = clazz.getConstructor(chat.getClass());
            command = (Command) constructor.newInstance(chat);
        } catch (
                NoSuchMethodException | SecurityException| InstantiationException |
                IllegalAccessException | InvocationTargetException |IllegalArgumentException e
        ) {
            log.error(e.getLocalizedMessage());
            return;
        }

        log.debug(command.getId() + " command recognized");
        commandsAwaitingResponse.remove(chat);

        command.process();
    }

    public void processAnswer(TgChat chat, String messageText) {
        Command command = commandsAwaitingResponse.get(chat);
        log.error(String.format("Answer by command %s recognized", command.getClass().toString()));
        command.processAnswer(messageText);
    }

    public boolean isAnswerExpecting(TgChat chat) {
        return commandsAwaitingResponse.containsKey(chat);
    }

    // Outgoing messages

    protected void sendMessage(TgChat chat, String text) {
        SendMessage message = new SendMessage(chat.getUser().getId(), text);
        message.setParseMode(ParseMode.HTML);

        App.getContext().getBotMessageSender()
                .send(message);
    }

    protected void sendMessage(TgChat chat, String text, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage(chat.getUser().getId(), text);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(keyboard);

        App.getContext().getBotMessageSender()
                .send(message);
    }
}
