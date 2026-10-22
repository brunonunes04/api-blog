package com.api.api_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiBlogApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiBlogApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
