package ru.sanddev.weathertgbot.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.db.user.User;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

@Log4j
@EqualsAndHashCode (of = "user")
public class Chat {

    @Getter
    private final User user;

    private DialogSupplier dialogSupplier;

    public Chat(User user) {
        this.user = user;
        this.dialogSupplier = new DialogSupplier(user.getLanguageCode());
    }

    public String getDialog(String key, Object... arg) {
        return dialogSupplier.getDialog(key, arg);
    }

    public void saveLanguage(String code) throws IllegalArgumentException {
        if (dialogSupplier.getLanguage().toString().equals(code))
            return;

        log.info(String.format("Change language to %s", code));

        dialogSupplier = new DialogSupplier(code);
        user.setLanguageCode(code);
        UserManager.save(user);
    }

    public Locale getLocale() {
        return dialogSupplier.getLocale();
    }
}
