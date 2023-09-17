package ru.sanddev.weathertgbot.weather;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.WeatherClient;
import ru.sanddev.WeatherClient.objects.WeatherToday;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 16.09.2023
 */

@Log4j
@Service
public class WeatherService {

    @Getter
    @Value("${weather.apiid}")
    private String apiId;

    public String getWeather(String city, Locale locale) {
        log.debug(String.format("Loading weather by today was started. Params: %s %s", city, locale.toString()));

        WeatherClient client = new WeatherClient(apiId, city);

        try {
            client.setLocale(locale);
        } catch (WeatherException e) {
            log.error(e.getLocalizedMessage(), e);
            return e.getLocalizedMessage();
        }

        WeatherToday weather;
        try {
            weather = client.loadWeatherToday();
        } catch (WeatherException e) {
            log.error(e.getLocalizedMessage(), e);
            return e.getLocalizedMessage();
        }

        return WeatherComposer.composeWeatherToday(weather, locale);
    }
}
