package ru.sanddev.weathertgbot.BotObjects;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.BotObjects.BotChat;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 27.05.2023
 */

@Service
@Log4j
public class BotMessageSender {

    @Getter @Setter
    private SendMessage message;

    public void send() {
        log.info(
                String.format("Sending message to chat %s: %s", message.getChatId(), message.getText())
        );

        try {
            AppWeatherBot.getContext().getBot()
                    .execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
