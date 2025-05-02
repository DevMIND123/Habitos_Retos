package com.retochimba.habitos.habitos_retos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HabitosRetosApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitosRetosApplication.class, args);
	}

}
