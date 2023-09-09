package ru.sanddev.weathertgbot.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 31.08.2023
 */

@Component
@Getter @Setter
public class DataBase {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ScheduledNotificationRepository scheduledNotificationRepository;
}
