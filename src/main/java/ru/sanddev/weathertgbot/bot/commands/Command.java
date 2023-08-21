package ru.sanddev.weathertgbot.bot.commands;

public interface Command {

    String getId();

    void process();
    void processAnswer(String messageText);
}
