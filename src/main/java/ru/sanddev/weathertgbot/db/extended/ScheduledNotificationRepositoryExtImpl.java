package ru.sanddev.weathertgbot.db.extended;

import org.springframework.stereotype.Repository;
import ru.sanddev.weathertgbot.db.ScheduledNotificationRepositoryExt;
import ru.sanddev.weathertgbot.db.entities.ScheduledNotification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Repository
public class ScheduledNotificationRepositoryExtImpl implements ScheduledNotificationRepositoryExt {
    @PersistenceContext
    private EntityManager em;

    public List<ScheduledNotification> findAllByTime(Time start, Time end) {
        String sql = "select * from scheduled_notifications where time between ? and ?";
        Query query = em.createNativeQuery(sql, ScheduledNotification.class)
                .setParameter(1, start)
                .setParameter(2, end);
        return query.getResultList();
    }
}