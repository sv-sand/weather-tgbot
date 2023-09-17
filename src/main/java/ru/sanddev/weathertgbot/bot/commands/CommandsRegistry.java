package ru.sanddev.weathertgbot.bot.commands;

import ru.sanddev.weathertgbot.bot.commands.impl.*;

import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 12.09.2023
 */

public class CommandsRegistry {

    public final static List<Class<? extends BaseCommand>> LIST_OF_COMMANDS = List.of(
            StartCommand.class,
            HelpCommand.class,
            LangCommand.class,
            CityCommand.class,
            WeatherCommand.class,
            NotifyCommand.class,
            NotificationsCommand.class,
            BreakCommand.class
    );
}
