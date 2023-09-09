package ru.sanddev.weathertgbot.bot.commands.impl;

import ru.sanddev.weathertgbot.bot.BotChat;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class UndefinedCommand extends BaseCommand {

    public static final String ID = "/undefined";

    public UndefinedCommand(BotChat chat) {
        super(chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        sendMessage(chat.getDialog("command_not_recognized"));
    }

    @Override
    public void processAnswer(String receivedMessageText) {
    }
}
