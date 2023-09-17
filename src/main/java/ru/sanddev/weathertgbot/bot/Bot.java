package ru.sanddev.weathertgbot.bot;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sanddev.weathertgbot.App;
import ru.sanddev.weathertgbot.db.UserManager;
import ru.sanddev.weathertgbot.db.entities.TgUser;

import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 13.05.2023
 */

@Component
@Log4j
public class Bot extends TelegramLongPollingBot {

    private final String token;
    private final String userName;

    @Autowired
    public Bot(
            @Value("${bot.token}") String token,
            @Value("${bot.username}") String userName
    ) {
        super(token);
        this.userName = userName;
        this.token = token;
    }

    public void applyMenu() {
        for (var languageCode : LanguageCode.values()) {
            BotMenu menu = new BotMenu(languageCode);
            setCommandMenu(menu.getBotCommands(), languageCode);
        }
    }

    private void setCommandMenu(List<BotCommand> commands, LanguageCode languageCode) {
        SetMyCommands set = new SetMyCommands(
                commands,
                new BotCommandScopeDefault(),
                languageCode.toString()
        );

        try {
            execute(set);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Got updates");
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            TgUser user = UserManager.get(update.getMessage().getFrom());
            TgChat chat = new TgChat(user);

            log.info(String.format("From user %s message received: %s", chat.getUser(), messageText));

            App.getContext().getCommandsService()
                    .processCommand(chat, messageText);

        } else if (update.hasCallbackQuery()) {
            String messageText = update.getCallbackQuery().getData();
            TgUser user = UserManager.read(update.getCallbackQuery().getMessage().getChat().getId().toString());
            TgChat chat = new TgChat(user);

            log.info(String.format("From user %s callback received: %s", chat.getUser(), messageText));

            App.getContext().getCommandsService()
                    .processAnswer(chat, messageText);
        }
    }
}
