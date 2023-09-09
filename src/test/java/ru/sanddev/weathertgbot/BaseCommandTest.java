package ru.sanddev.weathertgbot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import ru.sanddev.weathertgbot.bot.LanguageCode;
import ru.sanddev.weathertgbot.db.entities.User;

import java.util.ArrayList;

import static org.mockito.Mockito.spy;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 30.08.2023
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseCommandTest {

    protected abstract void test();

    @Test
    public void testCommand() {
        mockBot();
        test();
    }

    public void mockBot() {
        App.getContext().setBotMessageSender(
                spy(App.getContext().getBotMessageSender())
        );

        Mockito.doNothing()
                .when(App.getContext().getBotMessageSender())
                .send();
    }

    // Service methods

    protected void deleteAllUsers() {
        App.getContext().getDb().getUserRepository().deleteAll();
    }

    protected User saveUser(String id, String name, LanguageCode langCode) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setLanguageCode(langCode.toString());

        return App.getContext().getDb().getUserRepository().save(user);
    }

    protected Chat newChat(String id, String name) {
        return new Chat(
                Long.valueOf(id), "", "", "", "", name,
                new ChatPhoto(), "", "", new Message(), "",
                false, new ChatPermissions(), 0, "", 0L, new ChatLocation(),
                0, false, false, false,
                false, false, false, new ArrayList<>(),
                "", false, false
        );
    }

    protected void botSend(Chat chat, String messageText) {
        Message message = new Message();
        message.setChat(chat);
        message.setText(messageText);

        Update update = new Update();
        update.setMessage(message);

        App.getContext().getBot().onUpdateReceived(update);
    }

    protected SendMessage botAnswer() {
        return App.getContext().getBotMessageSender().getMessage();
    }
}
