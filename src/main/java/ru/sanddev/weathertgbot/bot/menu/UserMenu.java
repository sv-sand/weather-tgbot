package ru.sanddev.weathertgbot.bot.menu;

import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.bot.Language;
import ru.sanddev.weathertgbot.commands.impl.*;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 11.09.2023
 */

@Log4j
public class UserMenu extends MenuBase {

    public UserMenu(Language language) {
        super(language);
    }

    protected void fillCommands() {
        addCommand(StartCommand.ID);
        addCommand(WeatherCommand.ID);
        addCommand(NotificationsCommand.ID);
        addCommand(SettingsCommand.ID);
        addCommand(HelpCommand.ID);
    }
}
