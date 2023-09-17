package ru.sanddev.weathertgbot.bot.commands.impl;

import ru.sanddev.weathertgbot.bot.TgChat;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 28.08.2023
 */

public class NotificationsCommand extends BaseCommand {

    public static final String ID = "/notifications";

    public NotificationsCommand(TgChat chat) {
        super(chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        sendMessage(getText());
    }

    @Override
    public void processAnswer(String receivedMessageText) {
    }

    private String getText() {
        return chat.getDialog("created_notifications") + getTextPositions();
    }

    private String getTextPositions() {
        return "\n" +
                chat.getDialog("created_notifications_position",
                chat.getUser().getNotification().getTime().toString(),
                chat.getUser().getCity()
        );
    }
}
