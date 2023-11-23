package ru.sanddev.weathertgbot.bot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import ru.sanddev.weathertgbot.commands.CommandsService;
import ru.sanddev.weathertgbot.commands.impl.WeatherCommand;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommandRepository;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommand;
import ru.sanddev.weathertgbot.db.user.User;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationServiceTest {

    private static NotificationService notificationService;

    @BeforeAll
    public static void init() {
        mockNotificationService();
        mockSend();
        mockGetNow();
    }

    private static void mockNotificationService() {
        CommandsService commandsService = Mockito.mock(CommandsService.class);

        ScheduledCommandRepository repository = Mockito.mock(ScheduledCommandRepository.class);
        Mockito.when(repository.findAllByTime(Mockito.any(), Mockito.any()))
                .thenReturn(getScheduledCommands());

        notificationService = Mockito.spy(new NotificationService(commandsService, repository));
    }

    private static void mockSend() {
        try {
            PowerMockito.doNothing().when(notificationService, "send", Mockito.any());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void mockGetNow() {
        LocalTime time = LocalTime.of(6,1,5);
        try {
            PowerMockito.doReturn(time).when(notificationService, "getNow");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<ScheduledCommand> getScheduledCommands() {
        List<ScheduledCommand> result = new ArrayList<>();

        result.add(newScheduledCommand(WeatherCommand.ID, Time.valueOf("06:01:00")));
        result.add(newScheduledCommand(WeatherCommand.ID, Time.valueOf("06:01:01")));
        result.add(newScheduledCommand(WeatherCommand.ID, Time.valueOf("06:01:20")));
        result.add(newScheduledCommand(WeatherCommand.ID, Time.valueOf("06:01:59")));

        return result;
    }

    private static ScheduledCommand newScheduledCommand(String commandId, Time time) {
        ScheduledCommand command = new ScheduledCommand();

        command.setCommandID(commandId);
        command.setUser(Mockito.mock(User.class));
        command.setTime(time);

        return command;
    }

    @Test
    public void sendNotifications() {
        notificationService.startNotificationScheduler();

        assertEquals(notificationService.getStartInterval(), Time.valueOf("06:01:00"));
        assertEquals(notificationService.getStartInterval(), Time.valueOf("06:01:59"));

        //PowerMockito.verifyPrivate(notificationService, Mockito.times(4))
        //.send(any());
    }
}