package ru.sanddev.weathertgbot.bot;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Language {
    en,
    ru;

    public final static Language DEFAULT = ru;

    public Locale getLocale() {
        return new Locale(this.toString());
    }

    public String getName() {
        Map<Language, String> names = new HashMap();

        names.put(Language.en, "English");
        names.put(Language.ru, "Русский");

        return names.get(this);
    }
}
