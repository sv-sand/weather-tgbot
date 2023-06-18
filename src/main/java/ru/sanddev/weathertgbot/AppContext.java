package ru.sanddev.weathertgbot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sanddev.weathertgbot.BotObjects.BotMessageSender;
import ru.sanddev.weathertgbot.commands.CommandsService;
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
    private Bot bot;

    @Autowired
    private BotMessageSender botMessageSender;

    @Autowired
    private CommandsService commandsService;

    public AppContext() {
        AppWeatherBot.setContext(this);
    }
}
