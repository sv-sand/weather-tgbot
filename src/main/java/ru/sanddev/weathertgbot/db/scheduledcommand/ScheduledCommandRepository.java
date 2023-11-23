package ru.sanddev.weathertgbot.db.scheduledcommand;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Repository
public interface ScheduledCommandRepository
        extends CrudRepository<ScheduledCommand, Long>,
        ScheduledCommandRepositoryExt {
}
