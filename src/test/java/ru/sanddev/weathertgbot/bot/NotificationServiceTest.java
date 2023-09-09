package ru.sanddev.weathertgbot.bot;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
class NotificationServiceTest {

    @Spy
    NotificationService service = new NotificationService();

    @Test
    void sendNotifications() {
        Mockito.doNothing()
                .when(service)
                .send(Mockito.any());

        ZonedDateTime zdt = ZonedDateTime.now(ZoneOffset.UTC);
        service.setStartTimeInterval(new Time(00, zdt.getMinute(), 0));
        service.setEndTimeInterval(new Time(23, zdt.getMinute(), 0));

        service.sendNotifications();

        Mockito.verify(service, Mockito.times(0))
                .send(Mockito.any());
    }
}