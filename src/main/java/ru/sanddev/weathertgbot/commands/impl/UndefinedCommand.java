package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class UndefinedCommand extends BaseCommand {

    public UndefinedCommand() {
        super();
    }

    @Override
    public void send(BotChat chat) {
        sendingMessageText = chat.getDialog("command_not_recognized");
        super.send(chat);
    }

    @Override
    public void answer(BotChat chat, String messageText) {
        super.answer(chat, messageText);
    }
}
