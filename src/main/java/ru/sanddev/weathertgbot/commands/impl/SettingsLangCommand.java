package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.Language;
import ru.sanddev.weathertgbot.bot.keyboards.InlineKeyboard;
import ru.sanddev.weathertgbot.commands.BaseSettingsCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class SettingsLangCommand extends BaseSettingsCommand {

    public static final String ID = "/settings_lang";

    public SettingsLangCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        InlineKeyboard keyboard = new InlineKeyboard();
        addSettingsButton(keyboard.addRow(), LangChangeCommand.ID);

        Language lang = Language.valueOf(chat.getUser().getLanguageCode());

        sendDialog("lang_current", keyboard, lang.getName());
    }

    @Override
    public void processAnswer(String receivedMessageText) {

    }

}
