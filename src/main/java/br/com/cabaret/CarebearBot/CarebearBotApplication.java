package br.com.cabaret.CarebearBot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class CarebearBotApplication {
	
	@Autowired
	Bot bot;
	
	@PostConstruct
	public void postConstruct() {
		bot.create();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CarebearBotApplication.class, args);
	}
}
