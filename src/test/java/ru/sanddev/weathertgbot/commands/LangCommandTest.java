package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.BaseCommandTest;
import ru.sanddev.weathertgbot.db.entities.TgUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class LangCommandTest extends BaseCommandTest {

    @Override
    public void test() {
        createUser("1", "sand");

        Chat chat = newChat("1", "sand");
        SendMessage message;

        // Change language en -> en
        botSend(chat, "/lang");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText().lines().findFirst().orElseThrow(), "Please select language");

        botSend(chat, "en");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "Language was changed");

        // Change language en -> ru
        botSend(chat, "/lang");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText().lines().findFirst().orElseThrow(), "Please select language");

        botSend(chat, "ru");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "Язык успешно изменен");

        TgUser user = App.getContext().getDb().userRepository
                .findById(chat.getId().toString())
                .orElseThrow();
        assertEquals("ru", user.getLanguageCode());
    }
}
