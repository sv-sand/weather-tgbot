package ru.sanddev.weathertgbot.bot.menu;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.sanddev.weathertgbot.bot.Language;
import ru.sanddev.weathertgbot.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class MenuBase implements Menu {
    private final String RESOURCE_BUNDLE_NAME = "dialogs";
    private final ResourceBundle bundle;
    private List<BotCommand> botCommands = new ArrayList<>();

    protected abstract void fillCommands();

    public MenuBase(Language language) {
        this.bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, language.getLocale());
        fillCommands();
    }

    @Override
    public List<BotCommand> getBotCommands() {
        return botCommands;
    }

    protected void addCommand(String id) {
        id = id.replace(Command.PREFIX, "");

        botCommands.add(
                new BotCommand(id, bundle.getString(id))
        );
    }
}
