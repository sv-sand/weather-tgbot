package ru.sanddev.weathertgbot.bot;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 03.06.2023
 */

public class DialogService {
    private final String RESOURCE_BUNDLE_NAME = "dialogs";

    @Getter
    private final Locale locale;
    private final ResourceBundle dialogs;

    @Getter
    private final LanguageCode code;

    public DialogService(String languageCode) throws IllegalArgumentException {
        this.code = LanguageCode.valueOf(languageCode);
        this.locale = new Locale(languageCode);
        this.dialogs = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
    }

    public String getDialog(String key, Object... arg) {
        return String.format(
                dialogs.getString(key),
                arg
        );
    }
}
