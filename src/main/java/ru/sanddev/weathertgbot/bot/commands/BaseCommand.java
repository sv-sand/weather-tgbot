package ru.sanddev.weathertgbot.bot.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.bot.BotChat;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 05.06.2023
 */

public abstract class BaseCommand implements Command {

    protected final CommandsService service;
    protected final BotChat chat;

    public BaseCommand(BotChat chat) {
        this.service = AppWeatherBot.getContext().getCommandsService();
        this.chat = chat;
    }

    @Override
    public void process() {

    }

    @Override
    public void processAnswer(String receivedMessageText) {

    }

    protected void sendMessage(String text) {
        service.sendMessage(chat, text);
    }

    protected void sendMessage(String text, InlineKeyboardMarkup keyboard) {
        service.sendMessage(chat, text, keyboard);
    }

    protected void waitResponse() {
        service.getCommandsAwaitingResponse().put(chat, this);
    }

    protected void stopWaitingResponse() {
        service.getCommandsAwaitingResponse().remove(chat, this);
    }
}
