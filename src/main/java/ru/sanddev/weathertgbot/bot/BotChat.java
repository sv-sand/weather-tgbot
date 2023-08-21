package ru.sanddev.weathertgbot.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.db.entities.User;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

@Log4j
@EqualsAndHashCode (of = "user")
public class BotChat {

    @Getter
    private final User user;

    private BotDialogService dialogService;

    public BotChat(User user) {
        this.user = user;
        this.dialogService = new BotDialogService(user.getLanguageCode());
    }

    public String getDialog(String key, Object... arg) {
        return dialogService.getDialog(key, arg);
    }

    public void setLanguage(String code) throws IllegalArgumentException {
        if (dialogService.getCode().toString().equals(code))
            return;

        log.info(String.format("Change language to %s", code));

        dialogService = new BotDialogService(code);
        user.setLanguageCode(code);
        AppWeatherBot.getContext().userRepository
                .save(user);
    }

    public void setCity(String city) {
        log.info(String.format("Change city to %s", city));

        user.setCity(city);
        AppWeatherBot.getContext().userRepository
                .save(user);
    }

    public Locale getLocale() {
        return dialogService.getLocale();
    }
}
