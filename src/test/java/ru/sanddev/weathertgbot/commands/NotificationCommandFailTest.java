package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.BaseCommandTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class NotificationCommandFailTest extends BaseCommandTest {

    @Override
    public void test() {
        deleteAllUsers();
        createUser("1", "sand");

        Chat chat = newChat("1", "sand");
        botSend(chat, "/notifications");

        SendMessage message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText().lines().findFirst().get(), "No one notification have been created yet");
    }
}
