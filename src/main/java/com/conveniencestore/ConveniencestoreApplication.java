package com.conveniencestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ConveniencestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConveniencestoreApplication.class, args);
	}

}
