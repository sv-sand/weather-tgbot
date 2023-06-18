package ru.sanddev.weathertgbot.commands;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.sanddev.weathertgbot.BotObjects.BotChat;
import ru.sanddev.weathertgbot.commands.impl.HelpCommand;
import ru.sanddev.weathertgbot.commands.impl.LangCommand;
import ru.sanddev.weathertgbot.commands.impl.StartCommand;
import ru.sanddev.weathertgbot.commands.impl.UndefinedCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 11.06.2023
 */

@Log4j
@Service
public class CommandsService {

    @Getter
    private Map<BotChat, Command> activeCommands;

    public CommandsService() {
        activeCommands = new HashMap<>();
    }

    public void processCommand(BotChat chat, String messageText) {
        Command command;

        switch (messageText) {
            case "/start":
                log.debug("/start command recognized");
                command = new StartCommand();
                command.send(chat);
                break;

            case "/help":
                log.debug("/help command recognized");
                command = new HelpCommand();
                command.send(chat);
                break;

            case "/lang":
                log.debug("/lang command recognized");
                command = new LangCommand();
                command.send(chat);
                break;

            case "/break":
                log.debug("/break command recognized");
                activeCommands.remove(chat);
                break;

            default:

                if (!processAnswer(chat, messageText)) {
                    log.error("Command not recognized");
                    command = new UndefinedCommand();
                    command.send(chat);
                }
        }
    }

    private boolean processAnswer(BotChat chat, String messageText) {
        if (!activeCommands.containsKey(chat))
            return false;

        Command command = activeCommands.get(chat);
        log.error(String.format("Answer by command %s recognized", command.getClass().toString()));

        command.answer(chat, messageText);
        return true;
    }
}
