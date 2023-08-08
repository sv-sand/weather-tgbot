package ru.sanddev.weathertgbot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.BotObjects.BotLanguage;
import ru.sanddev.weathertgbot.commands.BaseCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class LangCommand extends BaseCommand {

    public static final String ID = "/lang";

    public LangCommand(BotChat chat) {
        super(chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        AppWeatherBot.getContext().getCommandsService().getActiveCommands().put(chat, this);

        // todo: refactor and test
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        keyboard.setKeyboard(rows);

        List<InlineKeyboardButton> row = new ArrayList<>();
        rows.add(row);

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("english");
        button.setCallbackData("en");
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("русский");
        button.setCallbackData("ru");
        row.add(button);

        sendMessage(chat.getDialog("support_languages"), keyboard);
        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        BotLanguage.Code code;

        try {
            code = BotLanguage.Code.valueOf(receivedMessageText);

            AppWeatherBot.getContext().getCommandsService().getActiveCommands().remove(chat);
            chat.setLanguage(code);

            sendMessage(chat.getDialog("language_changed"));

        } catch (IllegalArgumentException e) {
            sendMessage(chat.getDialog("cant_recognize_lang_code", receivedMessageText) + "\n" +
                    chat.getDialog("support_languages"));
        }

        super.processAnswer(receivedMessageText);
    }
}
