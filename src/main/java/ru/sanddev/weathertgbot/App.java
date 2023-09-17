package ru.sanddev.weathertgbot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Log4j
@SpringBootApplication
@EnableScheduling
@PropertySource("bot.properties")
public class App {

	@Getter @Setter
	private static Context context;

	public static void main(String[] args) {
		log.info("---");
		log.info("Start application");

		SpringApplication.run(App.class, args);
	}

	@EventListener({ContextRefreshedEvent.class})
	public void registerBot() throws TelegramApiException {
		TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
		api.registerBot(context.getBot());
		context.getBot().applyMenu();
	}
}
