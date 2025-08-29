package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.demo.service.KeelungSightsService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class Main {
    @Autowired
    private KeelungSightsService keelungSightsService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initializeDatabase() {
        return args -> {
            System.out.println("開始初始化資料庫");
            keelungSightsService.getAllSights();
            System.out.println("資料庫初始化完成");
        };
    }
}
