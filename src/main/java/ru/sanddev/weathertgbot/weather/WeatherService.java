package ru.sanddev.weathertgbot.weather;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.WeatherClient;
import ru.sanddev.WeatherClient.objects.WeatherToday;
import ru.sanddev.WeatherClient.objects.nested.CityData;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 16.09.2023
 */

@Log4j
@Service
public class WeatherService {

    @Getter
    private String apiId;

    @Getter @Setter
    private WeatherClient client;

    public WeatherService(@Value("${weather.apiid}") String apiId) {
        this.apiId = apiId;
        client = new WeatherClient(apiId);
    }

    public String loadWeather(String city, Locale locale) {
        log.info(String.format("Loading weather today was started. Params: %s, %s", city, locale.toString()));

        client.setCity(city);

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

    public CityData loadCity(String city) {
        log.debug(String.format("Load city %s", city));
        client.setCity(city);

        CityData cityData;
        try {
            cityData = client.loadWeatherToday()
                    .getCity();
        } catch (WeatherException e) {
            log.error(e.getLocalizedMessage(), e);
            cityData = new CityData();
        }
        return cityData;
    }
}
