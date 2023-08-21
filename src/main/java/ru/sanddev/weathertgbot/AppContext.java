package ru.sanddev.weathertgbot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sanddev.weathertgbot.bot.Bot;
import ru.sanddev.weathertgbot.bot.BotMessageSender;
import ru.sanddev.weathertgbot.bot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.ScheduledNotificationRepository;
import ru.sanddev.weathertgbot.db.UserRepository;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 07.06.2023
 */

@Component
@Getter @Setter
public class AppContext {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ScheduledNotificationRepository scheduledNotificationRepository;

    @Autowired
    private Config config;

    @Autowired
    private CommandsService commandsService;

    @Autowired
    private Bot bot;

    @Autowired
    private BotMessageSender botMessageSender;

    public AppContext() {
        AppWeatherBot.setContext(this);
    }
}
