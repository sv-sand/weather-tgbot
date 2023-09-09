package ru.sanddev.weathertgbot.bot.commands.impl;

import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.bot.BotChat;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 29.07.2023
 */

public class CityCommand extends BaseCommand {

    public static final String ID = "/city";

    public CityCommand(BotChat chat) {
        super(chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        App.getContext().getCommandsService().getCommandsAwaitingResponse().put(chat, this);
        sendMessage(chat.getDialog("type_city"));
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        App.getContext().getCommandsService().getCommandsAwaitingResponse().remove(chat);
        chat.setCity(receivedMessageText);

        sendMessage(chat.getDialog("city_changed"));
    }
}
