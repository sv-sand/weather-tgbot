package ru.sanddev.weathertgbot.bot;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * This class and send messages to user
 * @author sand <sve.snd@gmail.com>
 * @since 27.05.2023
 */

@Log4j
public class BotSender {
    private Bot bot;

    @Getter
    private BotApiMethod method;

    public BotSender(Bot bot) {
        this.bot = bot;
    }

    public void send(BotApiMethod method) {
        this.method = method;
        log.info(
                String.format("Sending method: %s", this.method)
        );

        try {
            bot.execute(method);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
