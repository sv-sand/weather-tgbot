package ru.sanddev.weathertgbot;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 13.05.2023
 */

@Log4j
@Component
public class BotInitializer {

    @Autowired
    public Bot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {

        log.debug("Telegram bot initialization");

        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
        }
        catch (TelegramApiException e) {
            log.error("Telegram bot initialization exception: " + e.getLocalizedMessage());
        }
    }
}
