package ru.sanddev.weathertgbot.commands.impl;

import ru.sanddev.weathertgbot.bot.Chat;
import ru.sanddev.weathertgbot.bot.keyboards.InlineKeyboard;
import ru.sanddev.weathertgbot.commands.BaseCommand;
import ru.sanddev.weathertgbot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommand;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 28.08.2023
 */

public class NotificationsCommand extends BaseCommand {

    public static final String ID = "/notifications";

    public NotificationsCommand(CommandsService commandsService, Chat chat) {
        super(commandsService, chat);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void process() {

        Set<ScheduledCommand> scheduledCommands = getScheduledCommands();
        if (scheduledCommands.isEmpty())
            sendEmptyList();
        else
            sendFilledList(scheduledCommands);
    }

    @Override
    public void processAnswer(String receivedMessageText) {
    }

    private Set<ScheduledCommand> getScheduledCommands() {
        List<ScheduledCommand> commands = getScheduledCommandRepository().findAllByUser(chat.getUser());

        return commands.stream()
                .filter(command -> command.getCommandID().equals("/weather"))
                .collect(Collectors.toSet());
    }

    private void sendEmptyList() {
        InlineKeyboard kbActions = new InlineKeyboard()
                .addButton(chat.getDialog("notifications_add"), "/notifications_add");

        sendDialog("notifications_empty", kbActions);
    }

    private void sendFilledList(Set<ScheduledCommand> scheduledCommands) {
        sendDialog("notifications_list");

        for (ScheduledCommand scheduledCommand : scheduledCommands) {
            String time = scheduledCommand.getTime()
                    .toLocalTime()
                    .plusHours(chat.getUser().getCity().getTimeZone())
                    .format(DateTimeFormatter.ofPattern("HH:mm"));

            String text = chat.getDialog("notifications_list_position",
                    time,
                    chat.getUser().getCity()
            );
            sendMessage(text, getKeyboardOfListPosition(scheduledCommand));
        }

        InlineKeyboard keyboard = new InlineKeyboard()
                .addButton(chat.getDialog("notifications_add"), "/notifications_add");

        sendDialog("notifications_actions", keyboard);
    }

    private InlineKeyboard getKeyboardOfListPosition(ScheduledCommand scheduledCommand) {
        String buttonText = chat.getDialog("notification_delete");
        String button_data = NotificationsDeleteCommand.ID + ":" + scheduledCommand.getId();

        return new InlineKeyboard()
                .addButton(buttonText, button_data);
    }
}
