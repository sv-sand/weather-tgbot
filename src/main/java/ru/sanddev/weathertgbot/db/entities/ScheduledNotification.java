package ru.sanddev.weathertgbot.db.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Time;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 10.08.2023
 */

@Entity(name = "scheduled_notifications")
@Data
@EqualsAndHashCode(of = "id")
public class ScheduledNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private TgUser user;

    private Time time;

    public boolean isEmpty() {
        return id == 0;
    }

    public String toString() {
        return String.format("Notification{id='%s', time='%s'}", id, time);
    }
}
