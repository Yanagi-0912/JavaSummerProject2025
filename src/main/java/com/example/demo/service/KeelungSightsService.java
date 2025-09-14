package com.example.demo.service;

import com.example.demo.controller.KeelungSightsCrawler;
import com.example.demo.model.Sight;
import com.example.demo.repository.SightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Service
public class KeelungSightsService {
    private static final Logger logger = LoggerFactory.getLogger(KeelungSightsService.class);
    private final SightsRepository sightsRepository;
    private final KeelungSightsCrawler crawler;
    private final DBInitializer dbInitializer;

    @Autowired
    public KeelungSightsService(SightsRepository sightsRepository, DBInitializer dbInitializer) {
        this.sightsRepository = sightsRepository;
        this.crawler = new KeelungSightsCrawler();
        this.dbInitializer = dbInitializer;
    }

    public List<Sight> getSightsByZone(String zone) {
        logger.info("Searching for sights in zone: {}", zone);
        List<Sight> sights = sightsRepository.findByZone(zone);
        if (sights.isEmpty()) {
            logger.info("No sights found for zone: {}. Crawling data...", zone);
            try {
                Sight[] crawledSights = crawler.getItems(zone);
                if (crawledSights != null && crawledSights.length > 0) {
                    sights = sightsRepository.saveAll(Arrays.asList(crawledSights));
                    logger.info("Successfully crawled and saved {} sights for zone: {}", crawledSights.length, zone);
                } else {
                    logger.warn("No sights found after crawling for zone: {}", zone);
                }
            } catch (Exception e) {
                logger.error("Error while crawling data for zone {}: {}", zone, e.getMessage());
                throw new RuntimeException("Failed to fetch sights for zone: " + zone, e);
            }
        }
        return sights;
    }

    public List<Sight> getAllSights() {
        List<Sight> allSights = sightsRepository.findAll();
        if (allSights.isEmpty()) {
            String[] zones = {"中山", "信義", "仁愛", "中正", "安樂", "七堵", "暖暖"};
            for (String zone : zones) {
                try {
                    List<Sight> zoneSights = getSightsByZone(zone);
                    allSights.addAll(zoneSights);
                } catch (Exception e) {
                    logger.error("Error fetching sights for zone {}: {}", zone, e.getMessage());
                }
            }
        }
        return allSights;
    }
}
