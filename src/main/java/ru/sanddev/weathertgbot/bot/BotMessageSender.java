package ru.sanddev.weathertgbot.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 27.05.2023
 */

@Service
@Log4j
public class BotMessageSender {

    private final Bot bot;

    @Getter @Setter
    private SendMessage message;

    public BotMessageSender(Bot bot) {
        this.bot = bot;
    }

    public void send() {
        log.info(
                String.format("Sending message to chat %s: %s", message.getChatId(), message.getText())
        );

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public void send(SendMessage message) {
        this.message = message;
        send();
    }
}
