package ru.sanddev.weathertgbot.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sanddev.weathertgbot.db.entities.TgUser;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Repository
public interface UserRepository extends CrudRepository<TgUser, String> {
}
