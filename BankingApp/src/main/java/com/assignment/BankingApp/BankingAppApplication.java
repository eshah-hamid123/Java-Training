package com.assignment.BankingApp;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAppApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("DB_TEST_PASSWORD", dotenv.get("DB_TEST_PASSWORD"));
		SpringApplication.run(BankingAppApplication.class, args);
	}
}
