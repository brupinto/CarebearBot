package br.com.cabaret.CarebearBot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class CarebearBotApplication {
	
	@Value("${botid}")
	private String botID;
	
	public static void main(String[] args) {
		SpringApplication.run(CarebearBotApplication.class, args);
	}
}
