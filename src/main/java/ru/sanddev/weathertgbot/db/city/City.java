package ru.sanddev.weathertgbot.db.city;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 16.11.2023
 */

@Entity(name = "city")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id = 0;

    @EqualsAndHashCode.Include
    private String name = "";

    @EqualsAndHashCode.Include
    private double latitude;

    @EqualsAndHashCode.Include
    private double longitude;

    private long timeZone;

    public boolean isEmpty() {
        return name.isEmpty();
    }

    public String toString() {
        return name;
    }
}
