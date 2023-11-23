package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class StartCommand extends BaseCommand {

    public static final String ID = "/start";

    public StartCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        if (chat.getUser().isNew())
            sendDialog("start_greeting", chat.getUser().getRepresentation());
        else
            sendDialog("start_welcome_back", chat.getUser().getRepresentation());
    }

    @Override
    public void processAnswer(String receivedMessageText) {
    }
}
