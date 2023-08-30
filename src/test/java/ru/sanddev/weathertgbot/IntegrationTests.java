package ru.sanddev.weathertgbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sanddev.weathertgbot.bot.Bot;
import ru.sanddev.weathertgbot.bot.BotInitializer;
import ru.sanddev.weathertgbot.bot.BotMessageSender;
import ru.sanddev.weathertgbot.bot.LanguageCode;
import ru.sanddev.weathertgbot.db.entities.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class IntegrationTests {

	@Autowired
	private AppContext context;

	@Autowired
	@SpyBean
	private Bot bot;

	@Autowired
	private BotMessageSender botMessageSender;

	@MockBean
	private BotInitializer botInitializer;

	@BeforeEach
	public void beforeEach() {
		try {
			Mockito.doReturn(new Message())
					.when(bot).execute(
							Mockito.any(BotApiMethod.class)
					);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void startCommandNewUser() {
		context.getUserRepository().deleteAll();

		Chat chat = newChat("1", "sand");
		botSend(chat, "/start");

		SendMessage message = botAnswer();
		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Привет sand, приятно познакомиться!");
	}

	@Test
	public void startCommandRu() {
		saveUser("1", "sand", LanguageCode.ru);

		Chat chat = newChat("1", "sand");
		botSend(chat, "/start");

		SendMessage message = botAnswer();
		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Рад видеть вас снова, sand");
	}

	@Test
	public void startCommandEn() {
		saveUser("1", "sand", LanguageCode.en);

		Chat chat = newChat("1", "sand");
		botSend(chat, "/start");

		SendMessage message = botAnswer();
		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Welcome back, sand");
	}

	@Test
	public void undefinedCommandRu() {
		saveUser("1", "sand", LanguageCode.ru);

		Chat chat = newChat("1", "sand");
		botSend(chat, "/undefined");

		SendMessage message = botAnswer();
		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Команда не поддерживается");
	}

	@Test
	public void undefinedCommandEn() {
		saveUser("1", "sand", LanguageCode.en);

		Chat chat = newChat("1", "sand");
		botSend(chat, "/undefined");

		SendMessage message = botAnswer();
		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Command not recognized");
	}

	@Test
	public void helpCommandRu() {
		saveUser("1", "sand", LanguageCode.ru);

		Chat chat = newChat("1", "sand");
		botSend(chat, "/help");

		SendMessage message = botAnswer();
		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText().lines().findFirst().orElseThrow(), "СПРАВКА");
	}

	@Test
	public void helpCommandEn() {
		saveUser("1", "sand", LanguageCode.en);

		Chat chat = newChat("1", "sand");
		botSend(chat, "/help");

		SendMessage message = botAnswer();
		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText().lines().findFirst().get(), "HELP");
	}

	@Test
	public void langCommandRu() {
		saveUser("1", "sand", LanguageCode.ru);

		Chat chat = newChat("1", "sand");
		SendMessage message;

		// Change language ru -> ru
		botSend(chat, "/lang");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText().lines().findFirst().orElseThrow(), "Пожалуйста, выберите язык");

		botSend(chat, "ru");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Язык успешно изменен");

		// Change language ru -> en
		botSend(chat, "/lang");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText().lines().findFirst().orElseThrow(), "Пожалуйста, выберите язык");

		botSend(chat, "en");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Language was changed");

		User user = context.userRepository.findById(chat.getId().toString())
				.orElseThrow();
		assertEquals("en", user.getLanguageCode());
	}

	@Test
	public void langCommandEn() {
		saveUser("1", "sand", LanguageCode.en);

		Chat chat = newChat("1", "sand");
		SendMessage message;

		// Change language en -> en
		botSend(chat, "/lang");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText().lines().findFirst().orElseThrow(), "Please select language");

		botSend(chat, "en");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Language was changed");

		// Change language en -> ru
		botSend(chat, "/lang");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText().lines().findFirst().orElseThrow(), "Please select language");

		botSend(chat, "ru");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Язык успешно изменен");

		User user = context.userRepository.findById(chat.getId().toString())
				.orElseThrow();
		assertEquals("ru", user.getLanguageCode());
	}

	@Test
	public void breakCommandEn() {
		saveUser("1", "sand", LanguageCode.en);

		Chat chat = newChat("1", "sand");
		SendMessage message;

		// Change language en -> en
		botSend(chat, "/lang");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText().lines().findFirst().orElseThrow(), "Please select language");

		botSend(chat, "/break");
		message = botAnswer();

		assertEquals(message.getChatId(), chat.getId().toString());
		assertEquals(message.getText(), "Command was broke");
	}

	// Service methods

	private User saveUser(String id, String name, LanguageCode langCode) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setLanguageCode(langCode.toString());

		return context.getUserRepository().save(user);
	}

	private Chat newChat(String id, String name) {
		return new Chat(
				Long.valueOf(id), "", "", "", "", name,
				new ChatPhoto(), "", "", new Message(), "",
				false, new ChatPermissions(), 0, "", 0L, new ChatLocation(),
				0, false, false, false,
				false, false, false, new ArrayList<>(),
				"", false, false
		);
	}

	private void botSend(Chat chat, String messageText) {
		Message message = new Message();
		message.setChat(chat);
		message.setText(messageText);

		Update update = new Update();
		update.setMessage(message);

		context.getBot().onUpdateReceived(update);
	}

	private SendMessage botAnswer() {
		return context.getBotMessageSender().getMessage();
	}
}
