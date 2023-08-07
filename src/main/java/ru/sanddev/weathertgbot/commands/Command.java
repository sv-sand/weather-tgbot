package ru.sanddev.weathertgbot.commands;

public interface Command {

    String getName();

    void process();
    void processAnswer(String messageText);
}
