package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.demo.service.DBInitializer;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class Main {
    @Autowired
    private DBInitializer dbInitializer;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            System.out.println("Starting database initialization...");
            dbInitializer.initializeDatabase();
            System.out.println("Database initialization process started.");
        };
    }
}
