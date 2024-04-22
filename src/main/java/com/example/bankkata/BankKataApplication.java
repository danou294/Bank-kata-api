package com.example.bankkata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("com.example.bankkata")
@EnableJpaRepositories("com.example.bankkata.domain.port")
public class BankKataApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankKataApplication.class, args);
	}

}
