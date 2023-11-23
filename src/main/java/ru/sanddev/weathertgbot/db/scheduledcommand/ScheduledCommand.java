package ru.sanddev.weathertgbot.db.scheduledcommand;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sanddev.weathertgbot.db.user.User;

import javax.persistence.*;
import java.sql.Time;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 10.08.2023
 */

@Entity(name = "scheduled_command")
@Data
@EqualsAndHashCode(of = "id")
public class ScheduledCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name="command_id")
    private String commandID = "";

    private Time time;

    public boolean isEmpty() {
        return id == 0;
    }

    public String toString() {
        return String.format("Notification{id='%s', time='%s'}", id, time);
    }
}
