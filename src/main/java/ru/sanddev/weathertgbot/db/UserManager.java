package ru.sanddev.weathertgbot.db;

import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.bot.LanguageCode;
import ru.sanddev.weathertgbot.db.entities.User;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 21.08.2023
 */

@Log4j
public class UserManager {

    /**
     * Read user from DB or register
     * @param chat - Telegram chat
     * @return user which was read or register
     */
    public static User get(Chat chat) {
        User user = read(chat.getId().toString());

        if (user.isEmpty())
            user = register(chat);

        return user;
    }

    /**
     * Read user from DB
     * @param chatId - id of user
     * @return User which was read
     */
    public static User read(String chatId) {
        log.debug("Read user from DB by ID=" + chatId);

        return AppWeatherBot.getContext().userRepository
                .findById(chatId)
                .orElse(new User());
    }

    /**
     * Write new user to DB
     * @param chat - Telegram chat
     * @return new registered user
     */
    public static User register(Chat chat) {
        log.debug("New user registration with ID=" + chat.getId());

        User user = new User();
        user.setId(chat.getId().toString());
        user.setName(chat.getUserName());
        user.setLanguageCode(LanguageCode.DEFAULT.toString());
        user.setNew(true);

        AppWeatherBot.getContext().userRepository
                .save(user);

        return user;
    }
}
