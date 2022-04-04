package br.com.cabaret.CarebearBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class CarebearBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarebearBotApplication.class, args);
		(new Bot()).create();
	}
}
