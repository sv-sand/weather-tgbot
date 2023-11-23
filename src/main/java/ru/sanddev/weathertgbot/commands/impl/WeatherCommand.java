package ru.sanddev.weathertgbot.commands.impl;

import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.city.City;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 17.07.2023
 */

@Log4j
public class WeatherCommand extends BaseCommand {

    public static final String ID = "/weather";

    public WeatherCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {

        City city = chat.getUser().getCity();
        if (city.isEmpty())
            requestCity();
        else
            sendWeather(city.getName());
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        stopWaitingResponse();
        sendWeather(receivedMessageText);
    }

    private void requestCity() {
        sendDialog("city_type");
        waitResponse();
    }

    public void sendWeather(String city) {
        String text = loadWeather(chat, city);
        sendMessage(text);
    }

    private String loadWeather(Chat chat, String city) {
        return getWeatherService().loadWeather(city, chat.getLocale());
    }
}
