package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.BaseCommandTest;
import ru.sanddev.weathertgbot.bot.LanguageCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class BreakCommandTest extends BaseCommandTest {

    @Override
    public void test() {
        createUser("1", "sand", LanguageCode.en);

        Chat chat = newChat("1", "sand");
        SendMessage message;

        // Change language en -> en
        botSend(chat, "/lang");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText().lines().findFirst().orElseThrow(), "Please select language");

        botSend(chat, "/break");
        message = botAnswer();

        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText(), "Command was broke");
    }
}
