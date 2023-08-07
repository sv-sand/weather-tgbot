package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.WeatherClient;
import ru.sanddev.WeatherClient.objects.WeatherToday;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.WeatherComposer;
import ru.sanddev.weathertgbot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 17.07.2023
 */

public class WeatherCommand extends BaseCommand {

    public WeatherCommand(BotChat chat) {
        super(chat);
        this.name = "/weather";
    }

    @Override
    public void process() {

        String city = chat.getUser().getCity();
        if (city.isEmpty()) {
            AppWeatherBot.getContext().getCommandsService().getActiveCommands().put(chat, this);
            sendMessage(chat.getDialog("type_city"));
        }
        else {
            sendWeather(city);
        }

        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        sendWeather(receivedMessageText);
        super.processAnswer(receivedMessageText);
    }

    private void sendWeather(String city) {
        String text = loadWeather(chat, city);
        sendMessage(text);
    }

    private String loadWeather(BotChat chat, String city) {

        String apiId = AppWeatherBot.getContext().getBot().getConfig().getApiId();
        WeatherClient client = new WeatherClient(apiId, city);

        WeatherToday weather;
        try {
            weather = client.loadWeatherToday();
        } catch (WeatherException e) {
            return e.getLocalizedMessage();
        }
        return WeatherComposer.composeWeatherToday(weather, chat.getLanguage().getLocale());
    }
}
