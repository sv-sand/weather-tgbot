package ru.sanddev.weathertgbot.commands;

import ru.sanddev.weathertgbot.bot.Chat;

import java.util.List;

public interface Command {

    String PREFIX = "/";

    String getId();
    Chat getChat();

    boolean hasParams();
    List<String> getParams();
    void setParams(List<String> params);

    void process();
    void processAnswer(String messageText);
}
