package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.BaseCommandTest;
import ru.sanddev.weathertgbot.bot.Language;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class NotificationsAddCommandTest extends BaseCommandTest {

    @Override
    public void test() {
        deleteAllUsers();
        createUser("1", "sand", Language.en, "Moscow");

        Chat chat = newChat("1", "sand");
        SendMessage message;

        botSend(chat, "/notify");
        message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "Select the hour of notification");

        botSend(chat, "13");
        message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "Select the minute of notification");

        botSend(chat, "09");
        message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "Notification was created");
    }
}
