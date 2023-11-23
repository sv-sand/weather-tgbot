package ru.sanddev.weathertgbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.09.2023
 */

@Component
public class BotInitializer {

    @Autowired
    private Bot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void registerBot() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);

        bot.init();
    }
}
