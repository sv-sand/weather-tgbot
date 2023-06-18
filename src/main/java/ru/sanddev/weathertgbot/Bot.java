package ru.sanddev.weathertgbot;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sanddev.weathertgbot.BotObjects.BotChat;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 13.05.2023
 */

@Component
@Log4j
public class Bot extends TelegramLongPollingBot {

    @Getter
    private BotConfig config;

    public Bot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Got updates");

        if (!update.hasMessage())
            return;

        BotChat chat = new BotChat(update.getMessage().getChat());

        if (!update.getMessage().hasText())
            return;

        String messageText = update.getMessage().getText();

        log.info(String.format("From user %s message received: %s", chat.getUser().getId(), messageText));

        AppWeatherBot.getContext().getCommandsService()
                .processCommand(chat, messageText);
    }
}
