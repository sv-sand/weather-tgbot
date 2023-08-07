package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class HelpCommand extends BaseCommand {

    public HelpCommand(BotChat chat) {
        super(chat);
        this.name = "/help";
    }

    @Override
    public void process() {
        sendMessage(chat.getDialog("help"));
        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        super.processAnswer(receivedMessageText);
    }
}
