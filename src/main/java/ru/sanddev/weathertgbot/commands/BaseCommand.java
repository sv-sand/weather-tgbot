package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.BotObjects.BotMessageSender;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 05.06.2023
 */

public abstract class BaseCommand implements Command {

    protected final BotMessageSender service;

    protected String sendingMessageText;

    public BaseCommand() {
        service = AppWeatherBot.getContext().getBotMessageSender();
    }

    @Override
    public void send(BotChat chat) {
        SendMessage message = new SendMessage(chat.getUser().getId(), sendingMessageText);
        service.setMessage(message);
        service.send();
    }

    @Override
    public void answer(BotChat chat, String messageText) {
        SendMessage message = new SendMessage(chat.getUser().getId(), sendingMessageText);
        service.setMessage(message);
        service.send();
    }
}
