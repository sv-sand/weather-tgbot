package ru.sanddev.weathertgbot.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.db.entities.TgUser;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

@Log4j
@EqualsAndHashCode (of = "user")
public class TgChat {

    @Getter
    private final TgUser user;

    private DialogService dialogService;

    public TgChat(TgUser user) {
        this.user = user;
        this.dialogService = new DialogService(user.getLanguageCode());
    }

    public String getDialog(String key, Object... arg) {
        return dialogService.getDialog(key, arg);
    }

    public void setLanguage(String code) throws IllegalArgumentException {
        if (dialogService.getCode().toString().equals(code))
            return;

        log.info(String.format("Change language to %s", code));

        dialogService = new DialogService(code);
        user.setLanguageCode(code);
        App.getContext().getDb().userRepository
                .save(user);
    }

    public void setCity(String city) {
        log.info(String.format("Change city to %s", city));

        user.setCity(city);
        App.getContext().getDb().userRepository
                .save(user);
    }

    public Locale getLocale() {
        return dialogService.getLocale();
    }
}
