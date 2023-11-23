package ru.sanddev.weathertgbot.commands.impl;

import lombok.extern.log4j.Log4j;
import ru.sanddev.WeatherClient.objects.nested.CityData;
import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.UserManager;
import ru.sanddev.weathertgbot.bot.keyboards.ReplyKeyboard;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.city.City;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommand;

import java.sql.Time;
import java.time.LocalTime;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

@Log4j
public class NotificationsAddCommand extends BaseCommand {

    public static final String ID = "/notifications_add";

    private final String [] availableHours = {"6", "7", "8", "9", "10"};
    private final String [] availableMinutes = {"00", "10", "20", "30", "40", "50"};

    private Integer hour;
    private Integer minute;
    private City city;

    private State state;
    enum State {
        CITY_INPUT,
        HOUR_INPUT,
        MINUTE_INPUT,
        FINISH
    }

    public NotificationsAddCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {

        if (chat.getUser().getCity().isEmpty())
            requestCity();
        else {
            city = chat.getUser().getCity();
            requestHour();
        }
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
    }

    private void requestCity() {
        sendDialog("city_type");
        state = State.CITY_INPUT;
        waitResponse();
    }

    private void requestHour() {
        ReplyKeyboard keyboard = new ReplyKeyboard();
        for (String hour: availableHours)
            keyboard.addButton(hour);

        sendDialog("notification_select_hour", keyboard);

        state = State.HOUR_INPUT;
        waitResponse();
    }

    private void requestMinutes() {
        ReplyKeyboard keyboard = new ReplyKeyboard();
        for (String minute: availableMinutes)
            keyboard.addButton(minute);

        sendDialog("notification_select_minute", keyboard);

        state = State.MINUTE_INPUT;
        waitResponse();
    }

    private void onCityInput(String cityName) {
        city = getCityRepository().findByName(cityName)
                .orElse(loadCity(cityName));

        if(city.isEmpty()) {
            sendDialog("city_cant_find");
            return;
        }

        requestHour();
    }

    private void onHourInput(String answerText) {
        int value;

        try {
            value = Integer.parseInt(answerText);
        } catch (NumberFormatException e) {
            log.warn("Not a integer value " + answerText);
            sendDialog("notification_not_a_valid_integer_value");
            return;
        }

        if (value < 0 || value > 24) {
            log.warn("Not a valid hour value " + value);
            sendDialog("notification_not_a_valid_hour_value");
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
            sendDialog("notification_not_a_valid_integer_value");
            return;
        }

        if (value < 0 || value > 60) {
            log.warn("Not a valid minute value " + value);
            sendDialog("notification_not_a_valid_minute_value");
            return;
        }
        log.debug(String.format("Value %s accepted as minute", value));

        minute = value;
        finishCommand();
    }

    private void finishCommand() {
        state = State.FINISH;
        stopWaitingResponse();

        UserManager.setCity(chat.getUser(), city);
        saveScheduledCommand();

        sendDialog("notifications_created");
    }

    private Time getUTCTime(long hourOffset) {
        LocalTime localTime = LocalTime.of(hour, minute)
                .minusHours(hourOffset);

        return Time.valueOf(localTime);
    }

    private City loadCity(String name) {
        CityData cityData = getWeatherService().loadCity(name);

        if(cityData.isEmpty())
            return new City();

        return getCityRepository().create(cityData);
    }

    private void saveScheduledCommand() {
        ScheduledCommand scheduledCommand = new ScheduledCommand();
        scheduledCommand.setCommandID(WeatherCommand.ID);
        scheduledCommand.setUser(chat.getUser());
        scheduledCommand.setTime(getUTCTime(city.getTimeZone()));

        getScheduledCommandRepository().save(scheduledCommand);
    }
}
