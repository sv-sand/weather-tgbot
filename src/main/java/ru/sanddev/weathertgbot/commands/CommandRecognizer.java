package ru.sanddev.weathertgbot.commands;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 08.10.2023
 */

public class CommandRecognizer {

    private Map<String, Class<? extends BaseCommand>> allCommands;

    @Getter
    private String messageText = "";

    @Getter
    private boolean isCommand;

    @Getter
    private String id = "";

    @Getter
    private Class<? extends BaseCommand> clazz;

    @Getter
    private List<String> params = new ArrayList<>();

    public CommandRecognizer(Map<String, Class<? extends BaseCommand>> allCommands) {
        this.allCommands = allCommands;
    }

    public void recognize(String messageText) {
        this.messageText = messageText;

        if (!messageText.startsWith(Command.PREFIX))
            return;

        if (messageText.indexOf(" ") >= 0)
            return;

        String [] lines = messageText.split(":");
        if (lines.length > 2)
            return;

        String commandId = lines[0];

        Class<? extends BaseCommand> commandClass = allCommands.get(commandId);
        if (commandClass == null)
            return;

        isCommand = true;
        id = commandId;
        clazz = commandClass;

        if (lines.length == 1)
            return;

        String stringOfParams = lines[1];
        params = Arrays.asList(stringOfParams.split(","));
    }
}
