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

    protected String name;

    protected final BotMessageSender service;
    protected final BotChat chat;

    public BaseCommand(BotChat chat) {
        this.name = "";
        this.service = AppWeatherBot.getContext().getBotMessageSender();
        this.chat = chat;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void process() {

    }

    @Override
    public void processAnswer(String receivedMessageText) {

    }

    protected void sendMessage(String text) {
        SendMessage message = new SendMessage(chat.getUser().getId(), text);
        service.setMessage(message);
        service.send();
    }
}
