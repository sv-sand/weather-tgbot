package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.BaseCommandTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class CityChangeCommandTest extends BaseCommandTest {

    @Override
    public void test() {
        deleteAllUsers();
        createUser("1", "sand");

        Chat chat = newChat("1", "sand");
        SendMessage message;

        botSend(chat, "/city");
        message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "Type your city");

        botSend(chat, "Kirov");
        message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "Can't find city, type another one");

        botSend(chat, "Moscow");
        message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "I remembered city");
    }
}
