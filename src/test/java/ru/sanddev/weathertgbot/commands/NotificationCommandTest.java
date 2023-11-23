package ru.sanddev.weathertgbot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.BaseCommandTest;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommandRepository;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommand;
import ru.sanddev.weathertgbot.db.user.User;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class NotificationCommandTest extends BaseCommandTest {

    @Autowired
    private ScheduledCommandRepository scheduledCommandRepository;

    @Override
    public void test() {
        deleteAllUsers();

        User user = createUser("1", "sand");

        ScheduledCommand notification = new ScheduledCommand();
        notification.setUser(user);
        notification.setTime(new Time(06,00,00));
        scheduledCommandRepository.save(notification);

        Chat chat = newChat("1", "sand");
        botSend(chat, "/notifications");

        SendMessage message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText().lines().findFirst().get(), "Here is created notifications:");
    }
}
