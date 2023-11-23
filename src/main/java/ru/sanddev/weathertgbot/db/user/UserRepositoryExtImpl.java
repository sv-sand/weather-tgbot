package ru.sanddev.weathertgbot.db.user;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 15.05.2023
 */

@Log4j
@Repository
public class UserRepositoryExtImpl implements UserRepositoryExt {
    @PersistenceContext
    private EntityManager em;

    public Optional<User> findByName(String name) {
        log.info(String.format("Searching user: ", name));

        String sql = "select * from users where name=?";
        Query query = em.createNativeQuery(sql, User.class)
                .setParameter(1, name);

        List<User> list = query.getResultList();
        if (list.isEmpty())
            return Optional.empty();

        return Optional.of(list.get(0));
    }

    @Override
    public List<User> findAllAdmins() {
        log.info("Searching admin users");

        String sql = "select * from users where is_admin=true";
        Query query = em.createNativeQuery(sql, User.class);

        return query.getResultList();
    }
}
