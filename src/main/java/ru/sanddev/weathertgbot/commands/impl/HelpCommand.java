package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class HelpCommand extends BaseCommand {

    public HelpCommand() {
        super();
    }

    @Override
    public void send(BotChat chat) {
        sendingMessageText = chat.getDialog("help");
        super.send(chat);
    }

    @Override
    public void answer(BotChat chat, String messageText) {
        super.answer(chat, messageText);
    }
}
