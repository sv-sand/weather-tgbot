package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.BaseCommand;

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
        AppWeatherBot.getContext().getCommandsService().getActiveCommands().put(chat, this);
        sendMessage(chat.getDialog("type_city"));
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        AppWeatherBot.getContext().getCommandsService().getActiveCommands().remove(chat);
        chat.setCity(receivedMessageText);

        sendMessage(chat.getDialog("city_changed"));
    }
}
