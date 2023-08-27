package ru.sanddev.weathertgbot.bot;

import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sanddev.weathertgbot.bot.commands.Command;
import ru.sanddev.weathertgbot.bot.commands.impl.WeatherCommand;
import ru.sanddev.weathertgbot.db.ScheduledNotificationRepository;
import ru.sanddev.weathertgbot.db.entities.ScheduledNotification;

import java.sql.Time;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.08.2023
 */

@Log4j
@Component
public class NotificationService {

    private final ScheduledNotificationRepository repo;
    private final BotMessageSender sender;

    public NotificationService(ScheduledNotificationRepository repo, BotMessageSender sender) {
        this.repo = repo;
        this.sender = sender;
    }

    @Scheduled(fixedRate = 60000)
    public void send() {
        log.info("Start notification scheduler");

        List<ScheduledNotification> notifications = repo.findAllByTime(getStartTime(), getEndTime());
        for (ScheduledNotification notification: notifications) {
            BotChat chat = new BotChat(notification.getUser());
            Command command = new WeatherCommand(chat);
            command.process();
        }
    }

    private Time getStartTime() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneOffset.UTC);
        return new Time(0, zdt.getMinute(), 0);
    }

    private Time getEndTime() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneOffset.UTC);
        return new Time(23, zdt.getMinute(), 59);
    }
}
