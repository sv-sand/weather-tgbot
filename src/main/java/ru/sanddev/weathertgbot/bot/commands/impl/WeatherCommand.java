package ru.sanddev.weathertgbot.bot.commands.impl;

import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.bot.TgChat;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 17.07.2023
 */

@Log4j
public class WeatherCommand extends BaseCommand {

    public static final String ID = "/weather";

    public WeatherCommand(TgChat chat) {
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

    private String loadWeather(TgChat chat, String city) {

        return App.getContext().getWeatherService()
                .getWeather(city, chat.getLocale());
    }
}
