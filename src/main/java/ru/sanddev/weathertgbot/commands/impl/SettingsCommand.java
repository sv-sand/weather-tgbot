package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.keyboards.InlineKeyboard;
import ru.sanddev.weathertgbot.commands.BaseSettingsCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class SettingsCommand extends BaseSettingsCommand {

    public static final String ID = "/settings";

    public SettingsCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        InlineKeyboard keyboard = new InlineKeyboard();

        addSettingsButton(keyboard.addRow(), SettingsLangCommand.ID);
        addSettingsButton(keyboard.addRow(), SettingsCityCommand.ID);

        sendDialog("settings", keyboard);
    }

    @Override
    public void processAnswer(String receivedMessageText) {

    }


}
