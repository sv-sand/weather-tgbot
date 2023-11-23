package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class BreakCommand extends BaseCommand {

    public static final String ID = "/break";

    public BreakCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        stopWaitingResponse();
        sendDialog("break_command_was_broke");
    }

    @Override
    public void processAnswer(String receivedMessageText) {
    }
}
