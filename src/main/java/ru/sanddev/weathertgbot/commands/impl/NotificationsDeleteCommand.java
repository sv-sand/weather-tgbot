package ru.sanddev.weathertgbot.commands.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.05.2023
 */

@Log4j
public class NotificationsDeleteCommand extends BaseCommand {

    public static final String ID = "/notification_delete";

    public NotificationsDeleteCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean hasParams() {
        return true;
    }

    @Override
    public void process() {
        if (params.size() == 0) {
            sendDialog("command_has_wrong_params");
            return;
        }

        Long id = Long.valueOf(params.get(0));

        try {
            getScheduledCommandRepository().deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            sendDialog("notifications_cant_find_by_id");
            return;
        }

        sendDialog("notification_was_deleted");
    }

    @Override
    public void processAnswer(String answerText) {
    }

}
