package ru.sanddev.weathertgbot.commands;

import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.db.entities.User;

public interface Command {
    void send(BotChat chat);
    void answer(BotChat chat, String messageText);
}
