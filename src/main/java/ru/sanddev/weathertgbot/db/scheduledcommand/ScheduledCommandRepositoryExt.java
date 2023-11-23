package ru.sanddev.weathertgbot.db.scheduledcommand;

import org.springframework.stereotype.Repository;
import ru.sanddev.weathertgbot.db.user.User;

import java.sql.Time;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Repository
public interface ScheduledCommandRepositoryExt {

    List<ScheduledCommand> findAllByTime(Time start, Time end);
    List<ScheduledCommand> findAllByUser(User user);
    void moveTimeAllByUser(User user, long hours);

}
