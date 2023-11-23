package ru.sanddev.weathertgbot.bot.keyboards;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 08.08.2023
 */

public class ReplyKeyboard {

    @Getter
    private final ReplyKeyboardMarkup keyboard;

    public ReplyKeyboard() {
        keyboard = new ReplyKeyboardMarkup();
        keyboard.setKeyboard(new ArrayList<>());
        addRow();
    }

    public ReplyKeyboard addRow() {
        keyboard.getKeyboard().add(new KeyboardRow());
        return this;
    }

    public ReplyKeyboard addButton(String text) {
        int index = keyboard.getKeyboard().size() - 1;
        keyboard.getKeyboard().get(index).add(text);
        return this;
    }
}
