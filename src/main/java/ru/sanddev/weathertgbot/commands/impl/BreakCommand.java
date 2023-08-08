package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.Command;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class BreakCommand extends BaseCommand {

    public static final String ID = "/break";

    public BreakCommand(BotChat chat) {
        super(chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        Command command = AppWeatherBot.getContext().getCommandsService().getActiveCommands().remove(chat);
        if (command != null) {
            sendMessage(chat.getDialog("command_was_broke", command.getId()));
        }

        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        super.processAnswer(receivedMessageText);
    }
}
