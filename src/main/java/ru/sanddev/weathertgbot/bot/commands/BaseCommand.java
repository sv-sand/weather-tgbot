package ru.sanddev.weathertgbot.bot.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.bot.BotChat;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 05.06.2023
 */

public abstract class BaseCommand implements Command {

    protected final BotChat chat;

    public BaseCommand(BotChat chat) {
        this.chat = chat;
    }

    protected void sendMessage(String text) {
        App.getContext().getCommandsService()
                .sendMessage(chat, text);
    }

    protected void sendMessage(String text, InlineKeyboardMarkup keyboard) {
        App.getContext().getCommandsService()
                .sendMessage(chat, text, keyboard);
    }

    protected void waitResponse() {
        App.getContext().getCommandsService()
                .getCommandsAwaitingResponse().put(chat, this);
    }

    protected void stopWaitingResponse() {
        App.getContext().getCommandsService()
                .getCommandsAwaitingResponse().remove(chat, this);
    }
}
