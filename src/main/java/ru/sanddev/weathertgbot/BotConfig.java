package ru.sanddev.weathertgbot;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 16.05.2023
 */

@Configuration
@Getter
@PropertySource("bot.properties")
public class BotConfig {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;
}
