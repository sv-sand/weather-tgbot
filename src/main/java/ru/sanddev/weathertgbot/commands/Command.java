package ru.sanddev.weathertgbot.commands;

import ru.sanddev.weathertgbot.BotObjects.BotChat;

public interface Command {
    void send(BotChat chat);
    void answer(BotChat chat, String messageText);
}
