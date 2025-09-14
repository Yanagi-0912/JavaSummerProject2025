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

    @Autowired
    public KeelungSightsService(SightsRepository sightsRepository) {
        this.sightsRepository = sightsRepository;
        this.crawler = new KeelungSightsCrawler();
    }

    public List<Sight> getSightsByZone(String zone) {
        logger.info("Searching for sights in zone: {}", zone);
        List<Sight> sights = sightsRepository.findByZone(zone);
        if (sights.isEmpty()) {
            logger.info("No sights found in database for zone: {}. Crawling new data.", zone);
            Sight[] crawledSights = crawler.getItems(zone);
            sights = sightsRepository.saveAll(Arrays.asList(crawledSights));
            logger.info("Saved {} new sights for zone: {}", sights.size(), zone);
        }
        return sights;
    }

    public List<Sight> getAllSights() {
        List<Sight> allSights = sightsRepository.findAll();
        if (allSights.isEmpty()) {
            String[] zones = {"中山", "信義", "仁愛", "中正", "安樂", "七堵", "暖暖"};
            for (String zone : zones) {
                allSights.addAll(getSightsByZone(zone));
            }
        }
        return allSights;
    }
}
