package ru.sanddev.weathertgbot.commands;

import lombok.Getter;
import lombok.Setter;
import ru.sanddev.weathertgbot.Context;
import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.keyboards.InlineKeyboard;
import ru.sanddev.weathertgbot.bot.keyboards.ReplyKeyboard;
import ru.sanddev.weathertgbot.db.city.CityRepository;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommandRepository;
import ru.sanddev.weathertgbot.weather.WeatherService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 05.06.2023
 */

public abstract class BaseCommand implements Command {

    @Getter
    protected final CommandsService commandsService;

    @Getter
    protected final Chat chat;

    @Getter @Setter
    protected List<String> params = new ArrayList<>();

    public BaseCommand(CommandsService commandsService, Chat chat) {
        this.commandsService = commandsService;
        this.chat = chat;
    }

    @Override
    public boolean hasParams() {
        return false;
    }

    protected void sendMessage(String text) {
        commandsService.send(chat, text);
    }

    protected void sendMessage(String text, InlineKeyboard keyboard) {
        commandsService.send(chat, text, keyboard);
    }

    protected void sendMessage(String text, ReplyKeyboard keyboard) {
        commandsService.send(chat, text, keyboard);
    }

    protected void sendDialog(String key, Object... arg) {
        sendMessage(chat.getDialog(key, arg));
    }

    protected void sendDialog(String key, InlineKeyboard keyboard, Object... arg) {
        sendMessage(chat.getDialog(key, arg), keyboard);
    }

    protected void sendDialog(String key, ReplyKeyboard keyboard, Object... arg) {
        sendMessage(chat.getDialog(key, arg), keyboard);
    }

    protected void waitResponse() {
        commandsService.getCommandsAwaitingResponse().put(chat, this);
    }

    protected void stopWaitingResponse() {
        commandsService.getCommandsAwaitingResponse().remove(chat);
    }

    protected CityRepository getCityRepository() {
        return Context.getBean(CityRepository.class);
    }

    protected ScheduledCommandRepository getScheduledCommandRepository() {
        return Context.getBean(ScheduledCommandRepository.class);
    }

    protected WeatherService getWeatherService() {
        return Context.getBean(WeatherService.class);
    }
}
