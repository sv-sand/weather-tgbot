package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class StartCommand extends BaseCommand {

    public StartCommand() {
        super();
    }

    @Override
    public void send(BotChat chat) {
        if (chat.isNew())
            sendingMessageText = chat.getDialog("hello_nice_to_meet_you", chat.getUser().getName());
        else
            sendingMessageText = chat.getDialog("welcome_back", chat.getUser().getName());

        super.send(chat);
    }

    @Override
    public void answer(BotChat chat, String messageText) {
        super.answer(chat, messageText);
    }
}
