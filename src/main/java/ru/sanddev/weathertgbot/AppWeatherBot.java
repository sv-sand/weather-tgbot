package ru.sanddev.weathertgbot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Log4j
@SpringBootApplication
@EnableScheduling
public class AppWeatherBot {

	@Autowired
	@Getter @Setter
	private static AppContext context;

	public static void main(String[] args) {
		log.info("---");
		log.info("Start application");

		SpringApplication.run(AppWeatherBot.class, args);
	}
}
