package ru.sanddev.weathertgbot.db;

import org.springframework.stereotype.Repository;
import ru.sanddev.weathertgbot.db.entities.ScheduledNotification;

import java.sql.Time;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Repository
public interface ScheduledNotificationRepositoryExt {
    List<ScheduledNotification> findAllByTime(Time time);
}
