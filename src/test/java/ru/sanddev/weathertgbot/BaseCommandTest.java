package ru.sanddev.weathertgbot;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import ru.sanddev.WeatherClient.Exception.WeatherException;
import ru.sanddev.WeatherClient.WeatherClient;
import ru.sanddev.WeatherClient.objects.WeatherToday;
import ru.sanddev.WeatherClient.objects.nested.CityData;
import ru.sanddev.weathertgbot.bot.Bot;
import ru.sanddev.weathertgbot.bot.BotSender;
import ru.sanddev.weathertgbot.bot.Language;
import ru.sanddev.weathertgbot.bot.UserManager;
import ru.sanddev.weathertgbot.db.city.City;
import ru.sanddev.weathertgbot.db.user.User;
import ru.sanddev.weathertgbot.weather.WeatherService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

@SpringBootTest
public abstract class BaseCommandTest {

    @Autowired
    protected Bot bot;

    @Autowired
    protected WeatherService weatherService;

    protected abstract void test();

    @PostConstruct
    public void init() {
        mockBot();
        mockWeatherService();
    }

    @Test
    public void testCommand() {
        test();
    }

    private void mockBot() {
        BotSender bms = spy(bot.getSender());
        bot.setSender(bms);

        Mockito.doNothing()
                .when(bms).send(Mockito.any());
    }

    private void mockWeatherService() {
        WeatherClient client = Mockito.mock(WeatherClient.class);
        weatherService.setClient(client);

        CityData cityData = new CityData();
        cityData.setName("Moscow");
        cityData.setTimezone(3);

        WeatherToday weather = new WeatherToday();
        weather.setCity(cityData);

        try {
            when(client.loadWeatherToday())
                    .thenReturn(weather);
        } catch (WeatherException e) {
            throw new RuntimeException(e);
        }
    }

    // Service methods

    protected void deleteAllUsers() {
        UserManager.deleteAll();
    }

    protected User createUser(String id, String name) {
        return createUser(id, name, Language.en);
    }

    protected User createUser(String id, String name, Language language) {
        return createUser(id, name, language, new City());
    }

    protected User createUser(String id, String name, Language language, City city) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setLanguageCode(language.toString());
        user.setCity(city);

        return UserManager.save(user);
    }

    protected Chat newChat(String id, String name) {
        return new Chat(
                Long.valueOf(id), "", "", name, "", name,
                new ChatPhoto(), "", "", new Message(), "",
                false, new ChatPermissions(), 0, "", 0L, new ChatLocation(),
                0, false, false, false,
                false, false, false, new ArrayList<>(),
                "", false, false
        );
    }

    protected void botSend(Chat chat, String messageText) {
        org.telegram.telegrambots.meta.api.objects.User user = new org.telegram.telegrambots.meta.api.objects.User(chat.getId(), chat.getFirstName(), false);
        user.setUserName(chat.getUserName());
        user.setLanguageCode(Language.DEFAULT.toString());

        Message message = new Message();
        message.setChat(chat);
        message.setText(messageText);
        message.setFrom(user);

        Update update = new Update();
        update.setMessage(message);

        bot.onUpdateReceived(update);
    }

    protected SendMessage botAnswer() {
        return (SendMessage) bot.getSender().getMethod();
    }
}
