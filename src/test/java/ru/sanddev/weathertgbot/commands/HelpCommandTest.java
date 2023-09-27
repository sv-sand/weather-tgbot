package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.BaseCommandTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class HelpCommandTest extends BaseCommandTest {

    @Override
    public void test() {
        createUser("1", "sand");

        Chat chat = newChat("1", "sand");
        botSend(chat, "/help");

        SendMessage message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText().lines().findFirst().get(), "HELP");
    }
}
