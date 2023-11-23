package ru.sanddev.weathertgbot.db.city;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sanddev.WeatherClient.objects.nested.CityData;

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
public class CityRepositoryExtImpl implements CityRepositoryExt {

    @PersistenceContext
    private EntityManager em;

    public Optional<City> findByName(String name) {
        log.info(String.format("Searching city: %s", name));

        String sql = "select * from city where name=?";
        Query query = em.createNativeQuery(sql, City.class)
                .setParameter(1, name);

        List<City> list = query.getResultList();
        if (list.isEmpty())
            return Optional.empty();

        return Optional.of(list.get(0));
    }

    @Transactional
    public City create(CityData data) {
        log.info(String.format("Creating city: %s", data.getName()));

        if (data.isEmpty()) {
            String message = String.format("Can't create city for empty data: %s", data);
            log.error(message);
            throw new RuntimeException(message);
        }

        City city = new City();
        city.setName(data.getName());
        city.setLatitude(data.getCoord().getLat());
        city.setLongitude(data.getCoord().getLon());
        city.setTimeZone(data.getTimezone());

        em.persist(city);

        return city;
    }
}
