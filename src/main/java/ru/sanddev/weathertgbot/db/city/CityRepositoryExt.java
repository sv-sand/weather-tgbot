package ru.sanddev.weathertgbot.db.city;

import org.springframework.stereotype.Repository;
import ru.sanddev.WeatherClient.objects.nested.CityData;

import java.util.Optional;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Repository
public interface CityRepositoryExt {
    Optional<City> findByName(String name);
    City create(CityData data);
}
