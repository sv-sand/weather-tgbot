package ru.sanddev.weathertgbot.bot;

import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sanddev.weathertgbot.AppWeatherBot;
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
public class SendNotification {

    private final ScheduledNotificationRepository repo;
    private final BotMessageSender sender;

    SendNotification() {
        this.repo = AppWeatherBot.getContext().getScheduledNotificationRepository();
        this.sender = AppWeatherBot.getContext().getBotMessageSender();
    }

    @Scheduled(fixedRate = 60000)
    public void send() {
        log.info("Start notification scheduler");

        List<ScheduledNotification> notifications = repo.findAllByTime(getCurrentTimeUTC());
        for (ScheduledNotification notification: notifications) {
            sendMessage(notification.getUser().getId(), "Test");
        }
    }

    private Time getCurrentTimeUTC() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneOffset.UTC);
        return new Time(zdt.getHour(), zdt.getMinute(), 0);
    }

    protected void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        message.setParseMode(ParseMode.HTML);

        sender.setMessage(message);
        sender.send();
    }
}
