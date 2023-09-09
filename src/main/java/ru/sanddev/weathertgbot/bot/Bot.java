package ru.sanddev.weathertgbot.bot;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.Config;
import ru.sanddev.weathertgbot.db.UserManager;
import ru.sanddev.weathertgbot.db.entities.User;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 13.05.2023
 */

@Component
@Log4j
public class Bot extends TelegramLongPollingBot {

    @Autowired
    public Bot(Config config) {
        super(config.getToken());
    }

    @Override
    public String getBotUsername() {
        return App.getContext().getConfig()
                .getUsername();
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Got updates");
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            User user = UserManager.get(update.getMessage().getChat());
            BotChat chat = new BotChat(user);

            log.info(String.format("From user %s message received: %s", chat.getUser(), messageText));

            App.getContext().getCommandsService()
                    .processCommand(chat, messageText);

        } else if (update.hasCallbackQuery()) {
            String messageText = update.getCallbackQuery().getData();
            User user = UserManager.read(update.getCallbackQuery().getMessage().getChat().getId().toString());
            BotChat chat = new BotChat(user);

            log.info(String.format("From user %s callback received: %s", chat.getUser(), messageText));

            App.getContext().getCommandsService()
                    .processAnswer(chat, messageText);
        }
    }
}
