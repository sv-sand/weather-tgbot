package ru.sanddev.weathertgbot.db;

import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.db.entities.TgUser;

import java.util.NoSuchElementException;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 21.08.2023
 */

@Log4j
public class UserManager {

    /**
     * Read user from DB or register
     * @param user - Telegram user
     * @return user which was read or register
     */
    public static TgUser get(User user) {
        TgUser tgUser;

        try {
            tgUser = read(user.getId().toString());
        } catch (NoSuchElementException e) {
            tgUser = register(user);
        }

        return tgUser;
    }

    /**
     * Read user from DB
     * @param userId - id of user
     * @throws NoSuchElementException – if no value is present
     * @return User which was read
     */
    public static TgUser read(String userId) throws NoSuchElementException {
        log.debug("Read user from DB by ID=" + userId);

        return App.getContext().getDb().getUserRepository()
                .findById(userId)
                .orElseThrow();
    }

    /**
     * Write new user to DB
     * @param user - Telegram user
     * @return new registered user
     */
    public static TgUser register(User user) {
        log.debug("New user registration with ID=" + user.getId());

        TgUser tgUser = new TgUser();
        tgUser.setId(user.getId().toString());
        tgUser.setName(user.getUserName());
        tgUser.setLanguageCode(user.getLanguageCode());
        tgUser.setNew(true);

        App.getContext().getDb().getUserRepository()
                .save(tgUser);

        return tgUser;
    }
}
