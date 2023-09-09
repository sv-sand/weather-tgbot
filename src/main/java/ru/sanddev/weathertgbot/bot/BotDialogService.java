package ru.sanddev.weathertgbot.bot;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 03.06.2023
 */

public class BotDialogService {

    @Getter
    private final Locale locale;
    private final ResourceBundle dialogs;

    @Getter
    private final LanguageCode code;

    public BotDialogService(String code) throws IllegalArgumentException {
        this.code = LanguageCode.valueOf(code);
        this.locale = new Locale(code);
        this.dialogs = ResourceBundle.getBundle("dialogs", locale);
    }

    public String getDialog(String key, Object... arg) {
        return String.format(
                dialogs.getString(key),
                arg
        );
    }
}
