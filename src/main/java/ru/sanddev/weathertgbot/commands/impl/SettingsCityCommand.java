package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.keyboards.InlineKeyboard;
import ru.sanddev.weathertgbot.commands.BaseSettingsCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class SettingsCityCommand extends BaseSettingsCommand {

    public static final String ID = "/settings_city";

    public SettingsCityCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        InlineKeyboard keyboard = new InlineKeyboard();
        addSettingsButton(keyboard.addRow(), CityChangeCommand.ID);

        String current = "";
        if (chat.getUser().getCity() != null)
            current = chat.getUser().getCity().getName();

        sendDialog("city_current", keyboard, current);
    }

    @Override
    public void processAnswer(String receivedMessageText) {

    }
}
