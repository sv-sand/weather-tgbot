package ru.sanddev.weathertgbot.db.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sanddev.weathertgbot.BotObjects.BotLanguage;

import javax.persistence.Entity;
import javax.persistence.Id;

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

    public User() {
        this.id = "";
        this.name = "";
        this.languageCode = BotLanguage.DEFAULT_LANGUAGE_CODE.toString();
    }

    @Override
    public String toString() {
        return String.format("User{id='%s', name='%s'}", id, name);
    }

    public boolean isEmpty() {
        return id.isEmpty();
    }

}
