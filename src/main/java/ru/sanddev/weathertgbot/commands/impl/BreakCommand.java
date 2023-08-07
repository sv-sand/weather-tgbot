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

    public BreakCommand(BotChat chat) {
        super(chat);
        this.name = "/break";
    }

    @Override
    public void process() {
        Command command = AppWeatherBot.getContext().getCommandsService().getActiveCommands().remove(chat);
        if (command != null) {
            sendMessage(chat.getDialog("command_was_broke", command.getName()));
        }

        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        super.processAnswer(receivedMessageText);
    }
}
