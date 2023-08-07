package ru.sanddev.weathertgbot.BotObjects;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 03.06.2023
 */

public class BotLanguage {

    public final static Code DEFAULT_LANGUAGE_CODE = Code.ru;

    @Getter
    private Locale locale;
    private ResourceBundle dialogs;

    @Getter
    private final Code code;

    public BotLanguage(Code code) {
        this.code = code;
        init();
    }

    private void init() {
        locale = new Locale(code.toString());
        dialogs = ResourceBundle.getBundle("dialogs", locale);
    }

    public String getDialog(String key, Object... arg) {
        return String.format(
                dialogs.getString(key),
                arg
        );
    }

    // Inner objects

    public enum Code {
        en,
        ru
    }
}
