package com.lexie.springbootbooks;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class SpringBootBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBooksApplication.class, args);
	}
}
