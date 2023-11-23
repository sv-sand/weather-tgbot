package ru.sanddev.weathertgbot.commands;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.keyboards.InlineKeyboard;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 21.11.2023
 */

public abstract class BaseSettingsCommand extends BaseCommand {

    public BaseSettingsCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    protected void addSettingsButton(InlineKeyboard keyboard, String commandId) {
        keyboard.addButton(getButtonText(commandId), commandId);
    }

    private String getButtonText(String commandId) {
        String key = commandId.replace(PREFIX, "");
        return  chat.getDialog(key);
    }
}
