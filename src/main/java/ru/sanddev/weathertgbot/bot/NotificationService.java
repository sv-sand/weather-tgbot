package ru.sanddev.weathertgbot.bot;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sanddev.weathertgbot.commands.Command;
import ru.sanddev.weathertgbot.commands.CommandsService;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommandRepository;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommand;

import java.sql.Time;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.08.2023
 */

@Log4j
@Component
public class NotificationService {

    private CommandsService commandsService;
    private ScheduledCommandRepository repository;

    @Getter
    private Time startInterval;

    @Getter
    private Time endInterval;

    @Autowired
    public NotificationService(CommandsService commandsService, ScheduledCommandRepository repository) {
        this.commandsService = commandsService;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 60000)
    public void startNotificationScheduler() {
        log.info("Start notification scheduler");

        LocalTime time = getNow();
        startInterval = Time.valueOf(time.withSecond(0));
        endInterval = Time.valueOf(time.withSecond(59));

        List<ScheduledCommand> notifications = getScheduledCommands();
        for (ScheduledCommand notification: notifications) {
            Chat chat = new Chat(notification.getUser());
            Class clazz = commandsService.getCommandManager().getAllCommands().get(notification.getCommandID());
            Command command = commandsService.newCommand(clazz, chat);

            send(command);
        }
    }

    private void send(Command command) {
        command.process();
    }

    private LocalTime getNow() {
        return LocalTime.now(ZoneOffset.UTC);
    }

    private List<ScheduledCommand> getScheduledCommands() {
        return repository.findAllByTime(startInterval, endInterval);
    }
}
