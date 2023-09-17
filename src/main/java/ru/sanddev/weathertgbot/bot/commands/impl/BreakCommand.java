package ru.sanddev.weathertgbot.bot.commands.impl;

import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.bot.TgChat;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class BreakCommand extends BaseCommand {

    public static final String ID = "/break";

    public BreakCommand(TgChat chat) {
        super(chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        App.getContext().getCommandsService().getCommandsAwaitingResponse().remove(chat);
        sendMessage(chat.getDialog("command_was_broke"));
    }

    @Override
    public void processAnswer(String receivedMessageText) {
    }
}
