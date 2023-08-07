package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class UndefinedCommand extends BaseCommand {

    public UndefinedCommand(BotChat chat) {
        super(chat);
        this.name = "/undefined";
    }

    @Override
    public void process() {
        sendMessage(chat.getDialog("command_not_recognized"));
        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        super.processAnswer(receivedMessageText);
    }
}
