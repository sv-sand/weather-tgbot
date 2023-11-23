package ru.sanddev.weathertgbot.bot.menu;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface Menu {
    List<BotCommand> getBotCommands();
}
