package ru.sanddev.weathertgbot.commands.impl;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.BotObjects.BotLanguage;
import ru.sanddev.weathertgbot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class LangCommand extends BaseCommand {

    public LangCommand() {
        super();
    }

    @Override
    public void send(BotChat chat) {
        AppWeatherBot.getContext().getCommandsService().getActiveCommands().put(chat, this);
        sendingMessageText = chat.getDialog("support_languages");
        super.send(chat);
    }

    @Override
    public void answer(BotChat chat, String messageText) {
        BotLanguage.Code code;

        try {
            code = BotLanguage.Code.valueOf(messageText);

            AppWeatherBot.getContext().getCommandsService().getActiveCommands().remove(chat);
            chat.setLanguage(code);

            sendingMessageText = chat.getDialog("language_changed");

        } catch (IllegalArgumentException e) {
            sendingMessageText = chat.getDialog("cant_recognize_lang_code", messageText) + "\n" +
                    chat.getDialog("support_languages");
        }

        super.answer(chat, messageText);
    }
}
