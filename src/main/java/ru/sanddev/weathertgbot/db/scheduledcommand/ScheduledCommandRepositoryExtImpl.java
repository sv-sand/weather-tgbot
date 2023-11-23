package ru.sanddev.weathertgbot.db.scheduledcommand;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sanddev.weathertgbot.db.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Log4j
@Repository
public class ScheduledCommandRepositoryExtImpl implements ScheduledCommandRepositoryExt {
    @PersistenceContext
    private EntityManager em;

    public List<ScheduledCommand> findAllByTime(Time start, Time end) {
        log.info(String.format("Searching commands by time interval: %s - %s", start.toString(), end.toString()));

        String sql = "SELECT * FROM scheduled_command WHERE time BETWEEN ? AND ?";
        Query query = em.createNativeQuery(sql, ScheduledCommand.class)
                .setParameter(1, start)
                .setParameter(2, end);

        return query.getResultList();
    }

    public List<ScheduledCommand> findAllByUser(User user) {
        log.info(String.format("Searching commands by user: %s", user.getName()));

        String sql = "select * from scheduled_command where user_id=?";
        Query query = em.createNativeQuery(sql, ScheduledCommand.class)
                .setParameter(1, user.getId());

        return query.getResultList();
    }

    @Transactional
    public void moveTimeAllByUser(User user, long hours) {
        List<ScheduledCommand> commands = findAllByUser(user);
        commands.stream()
                .forEach(command -> {
                    addHours(command, hours);
                    em.persist(command);
                });
    }

    private static void addHours(ScheduledCommand command, long hours) {
        LocalTime time = command.getTime().toLocalTime().plusHours(hours);
        command.setTime(Time.valueOf(time));
    }
}
