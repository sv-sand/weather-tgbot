package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.BaseCommandTest;
import ru.sanddev.weathertgbot.bot.LanguageCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class WeatherCommandRuTest extends BaseCommandTest {

    @Override
    public void test() {
        deleteAllUsers();
        saveUser("1", "sand", LanguageCode.ru);

        Chat chat = newChat("1", "sand");
        SendMessage message;

        botSend(chat, "/weather");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText().lines().findFirst().orElseThrow(), "Введите ваш город");

        botSend(chat, "Moscow");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertTrue(message.getText().indexOf("Погода на сегодня") > 0);
    }
}
