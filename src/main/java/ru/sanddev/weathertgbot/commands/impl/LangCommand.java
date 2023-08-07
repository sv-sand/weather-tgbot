package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.BotObjects.BotLanguage;
import ru.sanddev.weathertgbot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class LangCommand extends BaseCommand {

    public LangCommand(BotChat chat) {
        super(chat);
        this.name = "/lang";
    }

    @Override
    public void process() {
        AppWeatherBot.getContext().getCommandsService().getActiveCommands().put(chat, this);
        sendMessage(chat.getDialog("support_languages"));
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
