package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.BaseCommandTest;
import ru.sanddev.weathertgbot.bot.Language;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class WeatherCommandTest extends BaseCommandTest {

    @Override
    public void test() {
        deleteAllUsers();
        createUser("1", "sand", Language.en, "Moscow");

        Chat chat = newChat("1", "sand");
        SendMessage message;

        botSend(chat, "/weather");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertNotEquals(message.getText().indexOf("There is weather today"), -1);
        assertNotEquals(message.getText().indexOf("in city Moscow"), -1);
    }
}
