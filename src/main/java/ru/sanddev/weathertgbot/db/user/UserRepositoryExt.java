package ru.sanddev.weathertgbot.db.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Repository
public interface UserRepositoryExt {
    Optional<User> findByName(String name);
    List<User> findAllAdmins();
}
