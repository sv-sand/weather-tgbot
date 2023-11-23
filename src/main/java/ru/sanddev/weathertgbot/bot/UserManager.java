package ru.sanddev.weathertgbot.bot;

import lombok.extern.log4j.Log4j;
import ru.sanddev.weathertgbot.Context;
import ru.sanddev.weathertgbot.db.city.City;
import ru.sanddev.weathertgbot.db.scheduledcommand.ScheduledCommandRepository;
import ru.sanddev.weathertgbot.db.user.User;
import ru.sanddev.weathertgbot.db.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 21.08.2023
 */

@Log4j
public class UserManager {

    private static UserRepository getUserRepository() {
        return Context.getBean(UserRepository.class);
    }

    private static ScheduledCommandRepository getScheduledCommandRepository() {
        return Context.getBean(ScheduledCommandRepository.class);
    }

    /**
     * Read user from DB or register
     * @param telegramUser - Telegram user
     * @return user which was read or register
     */
    public static User findOrRegister(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        log.info("Searching telegram user = " + telegramUser);

        User user;
        try {
            user = findById(telegramUser.getId().toString());
        } catch (NoSuchElementException e) {
            user = register(telegramUser);
        }

        return user;
    }

    /**
     * Read user from DB
     * @param userId - id of user
     * @throws NoSuchElementException – if no value is present
     * @return User which was read
     */
    public static User findById(String userId) throws NoSuchElementException {
        log.info("Read user from DB by ID=" + userId);

        return getUserRepository()
                .findById(userId)
                .orElseThrow();
    }

    /**
     * Read user from DB
     * @param name - name of user
     * @throws NoSuchElementException – if no value is present
     * @return User which was read
     */
    public static User findByName(String name) throws NoSuchElementException {
        log.info("Read user from DB by name=" + name);

        return getUserRepository()
                .findByName(name)
                .orElseThrow();
    }

    /**
     * Read user from DB
     * @return List of admin Users which was read
     */
    public static List<User> findAllAdmins() {
        log.info("Read all admin users from DB");

        return getUserRepository().findAllAdmins();
    }

    /**
     * Write new user to DB
     * @param telegramUser - Telegram user
     * @return new registered user
     */
    public static User register(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        log.info("New user registration with ID=" + telegramUser.getId());

        User user = new User();
        user.setId(telegramUser.getId().toString());
        user.setName(telegramUser.getUserName());
        user.setFirsName(telegramUser.getFirstName());
        user.setLastName(telegramUser.getLastName());
        user.setLanguageCode(telegramUser.getLanguageCode());
        user.setNew(true);

        save(user);

        return user;
    }

    /**
     * Write user to DB
     * @param user
     * @return new registered user
     */
    public static User save(User user) {
        return getUserRepository().save(user);
    }

    /**
     * Delete all users from DB
     */
    public static void deleteAll() {
        getUserRepository().deleteAll();
    }

    /**
     * Change city in user
     * @param user - user for changing city
     * @param city - new city
     */
    public static void setCity(User user, City city) {
        if (user.getCity() == city)
            return;

        if (user.getCity() != null && !user.getCity().isEmpty())
            getScheduledCommandRepository()
                    .moveTimeAllByUser(user, user.getCity().getTimeZone() - city.getTimeZone());

        user.setCity(city);
        getUserRepository().save(user);
    }

    /**
     * Set admin flag by users in list
     * @param admins - list of names admin users
     */
    public static void setAdminUsers(List<String> admins) {
        log.info(String.format("Set admins: %s", admins));

        List<String> currentAdminNames = findAllAdmins()
                .stream()
                .map(user -> user.getName())
                .collect(Collectors.toList());

        takeOffAdminPrivilege(currentAdminNames, admins);
        setNewAdmins(currentAdminNames, admins);
    }

    private static void takeOffAdminPrivilege(List<String> currentAdminNames, List<String> newAdminNames) {
        List<String> notAdmins = new ArrayList<>(currentAdminNames);
        notAdmins.removeAll(newAdminNames);

        for (String name: notAdmins)
            setUserAsAdmin(name, false);
    }

    private static void setNewAdmins(List<String> currentAdminNames, List<String> newAdminNames) {
        List<String> newAdmins = new ArrayList<>(newAdminNames);
        newAdmins.removeAll(currentAdminNames);

        for (String name: newAdmins)
            setUserAsAdmin(name, true);
    }

    private static void setUserAsAdmin(String name, boolean isAdmin) {
        User user;
        try {
            user = findByName(name);
        } catch (NoSuchElementException e) {
            log.error(String.format("Cant find admin by name %s", name));
            return;
        }

        user.setAdmin(isAdmin);
        save(user);
    }

}
