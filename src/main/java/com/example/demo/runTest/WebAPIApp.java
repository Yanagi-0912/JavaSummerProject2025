package com.example.demo.runTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.demo")
public class WebAPIApp {

	public static void main(String[] args) {
		SpringApplication.run(WebAPIApp.class, args);
	}

}
