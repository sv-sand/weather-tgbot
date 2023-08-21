package ru.sanddev.weathertgbot.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sanddev.weathertgbot.db.entities.ScheduledNotification;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Repository
public interface ScheduledNotificationRepository
        extends CrudRepository<ScheduledNotification, Long>,
        ScheduledNotificationRepositoryExt {
}
