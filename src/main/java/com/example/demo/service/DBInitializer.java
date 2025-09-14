package com.example.demo.service;

import com.example.demo.controller.KeelungSightsCrawler;
import com.example.demo.model.Sight;
import com.example.demo.repository.SightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;

@Service
public class DBInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);
    private static final String[] ZONES = {"中山", "信義", "仁愛", "中正", "安樂", "七堵", "暖暖"};
    private static final long TIMEOUT_MINUTES = 3;

    private final SightsRepository sightsRepository;
    private final KeelungSightsCrawler crawler;

    @Autowired
    public DBInitializer(SightsRepository sightsRepository) {
        this.sightsRepository = sightsRepository;
        this.crawler = new KeelungSightsCrawler();
    }

    public void initializeDatabase() {
        logger.info("Starting database initialization...");
        CompletableFuture.runAsync(() -> {
            try {
                for (String zone : ZONES) {
                    logger.info("Crawling data for zone: {}", zone);
                    Sight[] sights = crawler.getItems(zone);
                    sightsRepository.saveAll(Arrays.asList(sights));
                    logger.info("Saved {} sights for zone: {}", sights.length, zone);
                }
                logger.info("Database initialization completed successfully");
            } catch (Exception e) {
                logger.error("Error during database initialization: {}", e.getMessage());
            }
        }).orTimeout(TIMEOUT_MINUTES, TimeUnit.MINUTES)
          .exceptionally(throwable -> {
              logger.error("Database initialization failed: {}", throwable.getMessage());
              return null;
          });
    }
}
