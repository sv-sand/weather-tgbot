package ru.sanddev.weathertgbot.bot.commands.impl;

import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.AppWeatherBot;
import ru.sanddev.weathertgbot.bot.BotChat;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;
import ru.sanddev.weathertgbot.bot.commands.KeyboardManager;
import ru.sanddev.weathertgbot.db.ScheduledNotificationRepository;
import ru.sanddev.weathertgbot.db.entities.ScheduledNotification;

import java.sql.Time;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

@Log4j
public class NotificationCommand extends BaseCommand {

    public static final String ID = "/notify";

    private final ScheduledNotificationRepository repo;
    private final String [] availableHours = {"6", "7", "8", "9", "10"};
    private final String [] availableMinutes = {"00", "10", "20", "30", "40", "50"};

    private Integer hour;
    private Integer minute;

    private State state;
    enum State {
        HOUR_INPUT,
        MINUTE_INPUT,
        FINISH
    }

    public NotificationCommand(BotChat chat) {
        super(chat);

        repo = AppWeatherBot.getContext().getScheduledNotificationRepository();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {
        waitResponse();

        KeyboardManager kbManager = new KeyboardManager();
        for (String hour: availableHours)
            kbManager.addButton(hour, hour);

        state = State.HOUR_INPUT;

        sendMessage(chat.getDialog("select_hour_notify"), kbManager.getKeyboard());
        super.process();
    }

    @Override
    public void processAnswer(String receivedMessageText) {

        Integer value;
        try {
            value = Integer.valueOf(receivedMessageText);
        } catch (NumberFormatException e) {
            log.warn("Not a integer value " + receivedMessageText);
            sendMessage(chat.getDialog("not_a_valid_integer_value"));
            return;
        }

        switch (state) {
            case HOUR_INPUT:
                processHourInput(value);
                break;
            case MINUTE_INPUT:
                processMinuteInput(value);
                finishCommand();
                break;
            default:
                String text = "Error E01: unexpected state of command";
                log.error(text);
                sendMessage(text);
        }

        super.processAnswer(receivedMessageText);
    }

    private void processHourInput(Integer value) {
        if (value < 0 || value > 24) {
            log.warn("Not a valid hour value " + value);
            sendMessage(chat.getDialog("not_a_valid_hour_value"));
            return;
        }
        log.debug(String.format("Value %s accepted as hour", value));

        hour = value;
        state = State.MINUTE_INPUT;

        KeyboardManager kbManager = new KeyboardManager();
        for (String minute: availableMinutes)
            kbManager.addButton(minute, minute);

        sendMessage(chat.getDialog("select_minute_notify"), kbManager.getKeyboard());
    }

    private void processMinuteInput(Integer value) {
        if (value < 0 || value > 60) {
            log.warn("Not a valid minute value " + value);
            sendMessage(chat.getDialog("not_a_valid_minute_value"));
            return;
        }
        log.debug(String.format("Value %s accepted as minute", value));

        minute = value;
        state = State.FINISH;
    }

    private void finishCommand() {
        ScheduledNotification notification = chat.getUser().getNotification();
        if (notification == null) {
            notification = new ScheduledNotification();
            notification.setUser(chat.getUser());
        }

        notification.setTime(new Time(hour, minute, 0));

        repo.save(notification);

        stopWaitingResponse();
        sendMessage(chat.getDialog("notify_was_created"));
    }
}
