package ru.sanddev.weathertgbot.commands;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.commands.impl.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
public class CommandManager {

    @Getter
    private Map<String, Class<? extends BaseCommand>> allCommands;

    @Getter
    private Map<String, Class<? extends BaseCommand>> userCommands;

    @Getter
    private Map<String, Class<? extends BaseCommand>> adminCommands;

    public CommandManager() {
        fillUserCommands();
        fillAdminCommands();
        fillAllCommands();
    }

    private void fillUserCommands() {
        List<Class<? extends BaseCommand>> list =  List.of(
                StartCommand.class,
                HelpCommand.class,
                WeatherCommand.class,
                BreakCommand.class,

                // Notifications
                NotificationsCommand.class,
                NotificationsAddCommand.class,
                NotificationsDeleteCommand.class,

                // Settings
                SettingsCommand.class,
                SettingsLangCommand.class,
                SettingsCityCommand.class,
                LangChangeCommand.class,
                CityChangeCommand.class
        );

        userCommands = getMap(list);
    }

    private void fillAdminCommands() {
        List<Class<? extends BaseCommand>> list = List.of(
                StartCommand.class,
                HelpCommand.class,
                WeatherCommand.class,
                BreakCommand.class,

                // Notifications
                NotificationsCommand.class,
                NotificationsAddCommand.class,
                NotificationsDeleteCommand.class,

                // Settings
                SettingsCommand.class,
                SettingsLangCommand.class,
                SettingsCityCommand.class,
                LangChangeCommand.class,
                CityChangeCommand.class
        );

        adminCommands = getMap(list);
    }

    private void fillAllCommands() {
        allCommands = new HashMap<>();
        allCommands.putAll(userCommands);
        allCommands.putAll(adminCommands);
    }

    private Map<String, Class<? extends BaseCommand>> getMap(List<Class<? extends BaseCommand>> list) {
        Map<String, Class<? extends BaseCommand>> commands = new HashMap<>();

        for (Class clazz : list) {
            try {
                commands.put(getCommandId(clazz), clazz);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                log.error("Command registry error", e);
            }
        }

        return commands;
    }

    private String getCommandId(Class<? extends BaseCommand> clazz) throws NoSuchFieldException, IllegalAccessException {
        return (String) clazz.getField("ID").get(null);
    }
}
