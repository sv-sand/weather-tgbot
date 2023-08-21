package ru.sanddev.weathertgbot.db.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 13.05.2023
 */

@Entity(name = "users")
@Data
@EqualsAndHashCode (of = "id")
public class User {

    @Id
    private String id;

    private String name;
    private String languageCode;
    private String city;

    @Transient
    @Getter @Setter
    private boolean isNew;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ScheduledNotification notification;

    public User() {
        this.id = "";
        this.name = "";
        this.languageCode = "ru";
    }

    @Override
    public String toString() {
        return String.format("User{id='%s', name='%s'}", id, name);
    }

    public boolean isEmpty() {
        return id.isEmpty();
    }

}
