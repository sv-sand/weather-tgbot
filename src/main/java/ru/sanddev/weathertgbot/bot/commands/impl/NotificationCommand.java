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
    private String city;

    private State state;
    enum State {
        CITY_INPUT,
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

        if (chat.getUser().getCity().isEmpty())
            requestCity();
        else
            requestHour();

        super.process();
    }

    @Override
    public void processAnswer(String answerText) {

        switch (state) {
            case CITY_INPUT:
                onCityInput(answerText);
                break;
            case HOUR_INPUT:
                onHourInput(answerText);
                break;
            case MINUTE_INPUT:
                onMinuteInput(answerText);
                break;
            default:
                String text = "Error C001: unexpected state of command " + ID;
                log.error(text);
                sendMessage(text);
        }

        super.processAnswer(answerText);
    }

    private void requestCity() {
        sendMessage(chat.getDialog("type_city"));
        state = State.CITY_INPUT;
        waitResponse();
    }

    private void requestHour() {
        KeyboardManager kbManager = new KeyboardManager();
        for (String hour: availableHours)
            kbManager.addButton(hour, hour);

        sendMessage(chat.getDialog("select_hour_notify"), kbManager.getKeyboard());

        state = State.HOUR_INPUT;
        waitResponse();
    }

    private void requestMinutes() {
        KeyboardManager kbManager = new KeyboardManager();
        for (String minute: availableMinutes)
            kbManager.addButton(minute, minute);

        sendMessage(chat.getDialog("select_minute_notify"), kbManager.getKeyboard());

        state = State.MINUTE_INPUT;
        waitResponse();
    }

    private void onCityInput(String answerText) {
        this.city = answerText;
        requestHour();
    }

    private void onHourInput(String answerText) {

        int value;
        try {
            value = Integer.parseInt(answerText);
        } catch (NumberFormatException e) {
            log.warn("Not a integer value " + answerText);
            sendMessage(chat.getDialog("not_a_valid_integer_value"));
            return;
        }

        if (value < 0 || value > 24) {
            log.warn("Not a valid hour value " + value);
            sendMessage(chat.getDialog("not_a_valid_hour_value"));
            return;
        }
        log.debug(String.format("Value %s accepted as hour", value));

        hour = value;
        requestMinutes();
    }

    private void onMinuteInput(String answerText) {

        int value;
        try {
            value = Integer.parseInt(answerText);
        } catch (NumberFormatException e) {
            log.warn("Not a integer value " + answerText);
            sendMessage(chat.getDialog("not_a_valid_integer_value"));
            return;
        }

        if (value < 0 || value > 60) {
            log.warn("Not a valid minute value " + value);
            sendMessage(chat.getDialog("not_a_valid_minute_value"));
            return;
        }
        log.debug(String.format("Value %s accepted as minute", value));

        minute = value;
        finishCommand();
    }

    private void finishCommand() {
        if(!chat.getUser().getCity().equals(city))
            chat.setCity(city);

        ScheduledNotification notification = chat.getUser().getNotification();
        if (notification == null || notification.isEmpty()) {
            notification = new ScheduledNotification();
            notification.setUser(chat.getUser());
        }

        notification.setTime(new Time(hour, minute, 0));
        repo.save(notification);

        sendMessage(chat.getDialog("notify_was_created"));

        state = State.FINISH;
        stopWaitingResponse();
    }
}
