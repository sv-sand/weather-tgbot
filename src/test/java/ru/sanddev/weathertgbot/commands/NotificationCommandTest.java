package ru.sanddev.weathertgbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.BaseCommandTest;
import ru.sanddev.weathertgbot.db.entities.ScheduledNotification;
import ru.sanddev.weathertgbot.db.entities.TgUser;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

public class NotificationCommandTest extends BaseCommandTest {

    @Override
    public void test() {
        deleteAllUsers();

        TgUser user = createUser("1", "sand");

        ScheduledNotification notification = new ScheduledNotification();
        notification.setUser(user);
        notification.setTime(new Time(06,00,00));
        App.getContext().getDb().getScheduledNotificationRepository()
                .save(notification);

        Chat chat = newChat("1", "sand");
        botSend(chat, "/notifications");

        SendMessage message = botAnswer();
        assertEquals(message.getChatId(), chat.getId().toString());
        assertEquals(message.getText().lines().findFirst().get(), "Here is created notifications:");
    }
}
