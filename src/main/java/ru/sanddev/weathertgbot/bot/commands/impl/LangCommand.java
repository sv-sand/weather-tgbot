package ru.sanddev.weathertgbot.bot.commands.impl;

import ru.sanddev.weathertgbot.bot.BotChat;
import ru.sanddev.weathertgbot.bot.LanguageCode;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;
import ru.sanddev.weathertgbot.bot.commands.KeyboardManager;

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
        waitResponse();

        KeyboardManager kbManager = new KeyboardManager()
                .addButton("english", "en")
                .addButton("русский", "ru");

        sendMessage(chat.getDialog("support_languages"), kbManager.getKeyboard());
        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        try {
            chat.setLanguage(receivedMessageText);
            stopWaitingResponse();

            sendMessage(chat.getDialog("language_changed"));

        } catch (IllegalArgumentException e) {
            sendMessage(chat.getDialog("cant_recognize_lang_code", receivedMessageText) + "\n" +
                    chat.getDialog("support_languages"));
        }

        super.processAnswer(receivedMessageText);
    }
}
