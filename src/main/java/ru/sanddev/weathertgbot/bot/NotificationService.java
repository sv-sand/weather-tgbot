package ru.sanddev.weathertgbot.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.bot.commands.Command;
import ru.sanddev.weathertgbot.bot.commands.impl.WeatherCommand;
import ru.sanddev.weathertgbot.db.entities.ScheduledNotification;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.08.2023
 */

@Log4j
@Component
public class NotificationService {

    @Getter @Setter
    private Time startTimeInterval;

    @Getter @Setter
    private Time endTimeInterval;

    @Scheduled(fixedRate = 60000)
    public void sendNotifications() {
        log.info("Start notification scheduler");

        setTimeInterval();

        List<ScheduledNotification> notifications = App.getContext().getDb().getScheduledNotificationRepository()
                .findAllByTime(startTimeInterval, endTimeInterval);

        for (ScheduledNotification notification: notifications) {
            TgChat chat = new TgChat(notification.getUser());
            Command command = new WeatherCommand(chat);

            send(command);
        }
    }

    public void send(Command command) {
        command.process();
    }

    public void setTimeInterval() {
        ZonedDateTime zdt = ZonedDateTime.now();

        startTimeInterval = new Time(zdt.getHour(), zdt.getMinute(), 0);
        endTimeInterval = new Time(zdt.getHour(), zdt.getMinute(), 59);
    }
}
