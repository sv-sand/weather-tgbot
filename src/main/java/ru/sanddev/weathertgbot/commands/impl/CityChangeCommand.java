package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.WeatherClient.objects.nested.CityData;
import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.UserManager;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.city.City;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 29.07.2023
 */

public class CityChangeCommand extends BaseCommand {

    public static final String ID = "/city_change";

    public CityChangeCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        waitResponse();
        sendDialog("city_type");
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        City city = setCityToUser(receivedMessageText);
        if(city.isEmpty())
            sendDialog("city_cant_find");
        else
            sendDialog("city_changed");
    }

    private City setCityToUser(String cityName) {
        City city = getCityRepository().findByName(cityName)
                .orElse(new City());

        if (city.isEmpty())
            city = loadCity(cityName);

        if(city.isEmpty())
            return city;

        stopWaitingResponse();
        UserManager.setCity(chat.getUser(), city);

        return city;
    }

    private City loadCity(String name) {
        CityData cityData = getWeatherService().loadCity(name);

        if(cityData.isEmpty())
            return new City();

        // City name retrieved in english always
        cityData.setName(name);

        return getCityRepository().create(cityData);
    }
}
