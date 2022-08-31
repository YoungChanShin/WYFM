package com.wyfm.critic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CriticApplication {

	public static void main(String[] args) {
		SpringApplication.run(CriticApplication.class, args);
	}

}
