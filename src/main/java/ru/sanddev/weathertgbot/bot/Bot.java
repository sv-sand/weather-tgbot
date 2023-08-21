package ru.sanddev.weathertgbot.bot;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.bot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.UserManager;
import ru.sanddev.weathertgbot.db.entities.User;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 13.05.2023
 */

@Component
@Log4j
public class Bot extends TelegramLongPollingBot {

    private final String name;
    private final CommandsService commandsService;

    public Bot() {
        super(AppWeatherBot.getContext().getConfig().getToken());

        this.name = AppWeatherBot.getContext().getConfig().getUsername();
        this.commandsService = AppWeatherBot.getContext().getCommandsService();
    }

    @Override
    public String getBotUsername() {
        return name;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Got updates");
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            User user = UserManager.get(update.getMessage().getChat());
            BotChat chat = new BotChat(user);

            log.info(String.format("From user %s message received: %s", chat.getUser(), messageText));

            commandsService.processCommand(chat, messageText);

        } else if (update.hasCallbackQuery()) {
            String messageText = update.getCallbackQuery().getData();
            User user = UserManager.read(update.getCallbackQuery().getMessage().getChat().toString());
            BotChat chat = new BotChat(user);

            log.info(String.format("From user %s callback received: %s", chat.getUser(), messageText));

            commandsService.processAnswer(chat, messageText);
        }
    }
}
