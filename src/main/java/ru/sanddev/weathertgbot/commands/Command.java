package ru.sanddev.weathertgbot.commands;

public interface Command {

    String getId();

    void process();
    void processAnswer(String messageText);
}
