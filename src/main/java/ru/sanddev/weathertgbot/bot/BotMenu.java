package ru.sanddev.weathertgbot.bot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.sanddev.weathertgbot.bot.commands.BaseCommand;
import ru.sanddev.weathertgbot.bot.commands.CommandsRegistry;

import java.util.*;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 11.09.2023
 */

@Log4j
public class BotMenu {
    private final String RESOURCE_BUNDLE_NAME = "bot-menu";
    private final String COMMAND_ID_PREFIX = "/";

    private final Map<String, String> menu = new LinkedHashMap<>();
    private final Map<String, Class<? extends BaseCommand>> registry = new HashMap<>();

    @Getter
    private final Locale locale;

    @Data
    @AllArgsConstructor
    public class BotMenuItem {
        private final String id;
        private final Class clazz;
        private final String description;
    }

    public BotMenu() {
        locale = new Locale(LanguageCode.DEFAULT.toString());
        init();
    }

    public BotMenu(LanguageCode languageCode) {
        locale = new Locale(languageCode.toString());
        init();
    }

    private void init() {
        fillRegistry();
        loadMenu();
    }

    private void loadMenu() {
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);

        Enumeration<String> enumeration = bundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String id = COMMAND_ID_PREFIX + key;

            if(registry.containsKey(id))
                menu.put(id, bundle.getString(key));
            else
                log.error(String.format("Command %s are not contains in registry", id));
        }
    }

    private void fillRegistry() {
        try {
            for (Class clazz : CommandsRegistry.LIST_OF_COMMANDS) {
                String id = (String) clazz.getField("ID").get(null);
                registry.put(id, clazz);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Command registry error", e);
        }
    }

    public List<BotCommand> getBotCommands() {
        List<BotCommand> list = new ArrayList<>();
        menu.entrySet().stream()
                .forEach(
                        item -> list.add(new BotCommand(item.getKey(), item.getValue()))
                );
        return list;
    }

    public Map<String, Class<? extends BaseCommand>> getAvailableCommands() {
        Map<String, Class<? extends BaseCommand>> commands = new HashMap<>();

        menu.entrySet().stream()
                .forEach(
                        item -> commands.put(item.getKey(), registry.get(item.getKey()))
                );

        return commands;
    }
}
