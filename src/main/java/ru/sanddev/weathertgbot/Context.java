package ru.sanddev.weathertgbot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sanddev.weathertgbot.bot.Bot;
import ru.sanddev.weathertgbot.bot.BotMessageSender;
import ru.sanddev.weathertgbot.bot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.DataBase;
import ru.sanddev.weathertgbot.weather.WeatherService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 07.06.2023
 */

@Component
@Getter @Setter
public class Context {

    @Autowired
    private DataBase db;

    @Autowired
    private CommandsService commandsService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private Bot bot;

    @Autowired
    private BotMessageSender botMessageSender;

    public Context() {
        App.setContext(this);
    }
}
