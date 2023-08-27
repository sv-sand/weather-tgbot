package ru.sanddev.weathertgbot.bot.commands.impl;

import ru.sanddev.weathertgbot.bot.BotChat;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class StartCommand extends BaseCommand {

    public static final String ID = "/start";

    public StartCommand(BotChat chat) {
        super(chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        if (chat.getUser().isNew())
            sendMessage(chat.getDialog("hello_nice_to_meet_you", chat.getUser().getName()));
        else
            sendMessage(chat.getDialog("welcome_back", chat.getUser().getName()));

        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        super.processAnswer(receivedMessageText);
    }
}