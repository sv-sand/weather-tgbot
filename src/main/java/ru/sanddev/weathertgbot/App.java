package ru.sanddev.weathertgbot;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Log4j
@SpringBootApplication
@EnableScheduling
@PropertySource("bot.properties")
public class App {

	public static void main(String[] args) {
		log.info("---");
		log.info("Start application");

		SpringApplication.run(App.class, args);
	}
}
