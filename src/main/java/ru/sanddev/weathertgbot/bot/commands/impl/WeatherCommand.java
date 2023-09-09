package ru.sanddev.weathertgbot.bot.commands.impl;

import lombok.extern.log4j.Log4j;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.WeatherClient;
import ru.sanddev.WeatherClient.objects.WeatherToday;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.bot.BotChat;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;
import ru.sanddev.weathertgbot.weather.WeatherComposer;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 17.07.2023
 */

@Log4j
public class WeatherCommand extends BaseCommand {

    public static final String ID = "/weather";

    public WeatherCommand(BotChat chat) {
        super(chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {

        String city = chat.getUser().getCity();
        if (city.isEmpty())
            requestCity();
        else
            sendWeather(city);
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        stopWaitingResponse();
        sendWeather(receivedMessageText);
    }

    private void requestCity() {
        sendMessage(chat.getDialog("type_city"));
        waitResponse();
    }

    public void sendWeather(String city) {
        String text = loadWeather(chat, city);
        sendMessage(text);
    }

    private String loadWeather(BotChat chat, String city) {

        String apiId = App.getContext().getConfig().getApiId();
        WeatherClient client = new WeatherClient(apiId, city);

        try {
            client.setLocale(chat.getLocale());
        } catch (WeatherException e) {
            log.error(e.getLocalizedMessage(), e);
        }

        WeatherToday weather;
        try {
            weather = client.loadWeatherToday();
        } catch (WeatherException e) {
            log.error(e.getLocalizedMessage(), e);
            return e.getLocalizedMessage();
        }
        return WeatherComposer.composeWeatherToday(weather, chat.getLocale());
    }
}
