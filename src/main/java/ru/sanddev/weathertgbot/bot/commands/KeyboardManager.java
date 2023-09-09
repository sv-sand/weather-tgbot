package ru.sanddev.weathertgbot.bot.commands;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 08.08.2023
 */

public class KeyboardManager {

    @Getter
    private final InlineKeyboardMarkup keyboard;

    public KeyboardManager() {
        keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(new ArrayList<>());
        addRow();
    }

    public KeyboardManager addRow() {
        keyboard.getKeyboard().add(new ArrayList<>());
        return this;
    }

    public KeyboardManager addButton(String text, String data) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(data);

        int index = keyboard.getKeyboard().size() - 1;
        keyboard.getKeyboard().get(index).add(button);

        return this;
    }
}
