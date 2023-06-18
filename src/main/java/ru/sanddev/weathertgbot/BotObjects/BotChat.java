package ru.sanddev.weathertgbot.BotObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.db.entities.User;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

@Log4j
@EqualsAndHashCode (of = "user")
public class BotChat {

    @Getter
    private User user;

    @Getter
    private boolean isNew;

    private BotLanguage lang;

    public BotChat(Chat chat) {
        readUser(chat);

        var code = BotLanguage.Code.valueOf(user.getLanguageCode());
        lang = new BotLanguage(code);
    }

    public String getDialog(String key, Object... arg) {
        return lang.getDialog(key, arg);
    }

    public void setLanguage(BotLanguage.Code code) {
        if (code == lang.getCode())
            return;

        log.info(String.format("Change language to %s", code.toString()));

        lang = new BotLanguage(code);
        user.setLanguageCode(code.toString());
        AppWeatherBot.getContext().userRepository
                .save(user);
    }

    // DB Methods

    private void readUser(Chat chat) {
        log.info("Read user from DB by ID=" + chat.getId());

        user = AppWeatherBot.getContext().userRepository
                .findById(chat.getId().toString())
                .orElse(new User());

        if (user.isEmpty()) {
            isNew = true;
            registerNewUser(chat);
        }
    }

    private void registerNewUser(Chat chat) {
        log.info("New user registration with ID=" + chat.getId());

        user = new User();
        user.setId(chat.getId().toString());
        user.setName(chat.getUserName());

        AppWeatherBot.getContext().userRepository
                .save(user);
    }

}
