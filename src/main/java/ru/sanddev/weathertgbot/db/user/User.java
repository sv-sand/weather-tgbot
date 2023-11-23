package ru.sanddev.weathertgbot.db.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sanddev.weathertgbot.db.city.City;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommand;

import javax.persistence.*;
import java.util.Set;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 13.05.2023
 */

@Entity(name = "users")
@Data
@EqualsAndHashCode (of = "id")
public class User {

    @Id
    private String id = "";
    private String name = "";
    private String firsName = "";
    private String lastName = "";

    @Column(name="is_admin")
    private boolean isAdmin;

    @Column(name="language_code")
    private String languageCode = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Transient
    @Column(name="is_new")
    private boolean isNew;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ScheduledCommand> scheduledCommands;

    public String getRepresentation() {
        if (!firsName.isEmpty())
            return firsName;

        return name;
    }

    @Override
    public String toString() {
        return String.format("User{id='%s', name='%s'}", id, name);
    }

    public boolean isEmpty() {
        return id.isEmpty();
    }
}
