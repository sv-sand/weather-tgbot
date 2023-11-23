package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.bot.keyboards.InlineKeyboard;
import ru.sanddev.weathertgbot.commands.CommandsService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

public class LangChangeCommand extends BaseCommand {

    public static final String ID = "/lang_change";

    public LangChangeCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        waitResponse();

        InlineKeyboard kb = new InlineKeyboard()
                .addButton("english", "en")
                .addButton("русский", "ru");

        sendDialog("lang_type", kb);
    }

    @Override
    public void processAnswer(String receivedMessageText) {
        try {
            chat.saveLanguage(receivedMessageText);
            stopWaitingResponse();

            sendDialog("lang_changed");

        } catch (IllegalArgumentException e) {
            sendDialog("lang_cant_recognize",
                    receivedMessageText + "\n" + chat.getDialog("lang_type")
            );
        }
    }
}
