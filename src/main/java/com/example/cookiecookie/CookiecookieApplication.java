package com.example.cookiecookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CookiecookieApplication {
	public static void main(String[] args) {
		SpringApplication.run(CookiecookieApplication.class, args);
	}
}
