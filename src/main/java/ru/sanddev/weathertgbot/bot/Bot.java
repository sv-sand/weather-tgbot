package ru.sanddev.weathertgbot.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.sanddev.weathertgbot.bot.menu.Menu;
import ru.sanddev.weathertgbot.bot.menu.UserMenu;
import ru.sanddev.weathertgbot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.user.User;

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
    private final List<String> admins;

    @Getter @Setter
    private BotSender sender;

    @Autowired
    private CommandsService commandsService;

    @Autowired
    public Bot(
            @Value("${bot.token}") String token,
            @Value("${bot.username}") String userName,
            @Value("#{'${bot.admins}'.split(',')}") List<String> admins
    ) {
        super(token);

        this.token = token;
        this.userName = userName;
        this.admins = admins;

        this.sender = new BotSender(this);
    }

    public void init() {
        commandsService.init(this);
        setMenu();
        UserManager.setAdminUsers(admins);
    }

    private void setMenu() {
        for (Language language : Language.values()) {
            Menu menu = new UserMenu(language);
            setLocalizedMenu(menu.getBotCommands(), language.toString());
        }
    }

    private void setLocalizedMenu(List<BotCommand> commands, String langCode) {
        SetMyCommands set = new SetMyCommands(
                commands,
                new BotCommandScopeDefault(),
                langCode
        );
        sender.send(set);
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

        if (update.hasMessage() && update.getMessage().hasText())
            onReceiveMessage(update.getMessage());
        else if (update.hasCallbackQuery())
            onReceiveCallBack(update.getCallbackQuery());
    }

    private void onReceiveMessage(Message message) {
        User user = UserManager.findOrRegister(message.getFrom());
        Chat chat = new Chat(user);

        log.info(String.format("From user %s message received: %s", chat.getUser(), message.getText()));

        commandsService.processMessageText(chat, message.getText());
    }

    private void onReceiveCallBack(CallbackQuery callback) {
        User user = UserManager.findById(callback.getMessage().getChat().getId().toString());
        Chat chat = new Chat(user);

        log.info(String.format("From user %s callback received: %s", chat.getUser(), callback.getData()));

        commandsService.processMessageText(chat, callback.getData());
    }

    public void send(SendMessage message) {
        sender.send(message);
    }

    public void send(Chat chat, String text) {
        SendMessage message = new SendMessage(chat.getUser().getId(), text);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(new ReplyKeyboardRemove(true));
        sender.send(message);
    }

    public void send(Chat chat, String text, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage(chat.getUser().getId(), text);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(keyboard);
        sender.send(message);
    }

    public void send(Chat chat, String text, ReplyKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage(chat.getUser().getId(), text);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(keyboard);
        sender.send(message);
    }
}
