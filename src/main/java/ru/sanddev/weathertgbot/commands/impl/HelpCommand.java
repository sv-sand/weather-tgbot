package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class HelpCommand extends BaseCommand {

    public static final String ID = "/help";

    public HelpCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        sendDialog("help_text");
    }

    @Override
    public void processAnswer(String receivedMessageText) {
    }
}
