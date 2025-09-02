package com.example.demo.service;

import com.example.demo.model.KeelungSightsCrawler;
import com.example.demo.model.Sight;
import com.example.demo.repository.SightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class KeelungSightsService {
    private final SightsRepository sightsRepository;
    private final KeelungSightsCrawler crawler;

    @Autowired
    public KeelungSightsService(SightsRepository sightsRepository) {
        this.sightsRepository = sightsRepository;
        this.crawler = new KeelungSightsCrawler();
    }

    public List<Sight> crawlAndSaveSightsByZone(String zone) {
        Sight[] sights = crawler.getItems(zone);

        return sightsRepository.saveAll(Arrays.asList(sights));
    }

    public List<Sight> getSightsByZone(String zone) {
        List<Sight> sights = sightsRepository.findByZone(zone);
        if (sights.isEmpty()) {
            sights = crawlAndSaveSightsByZone(zone);
        }

        return sights;
    }

    public List<Sight> getAllSights() {
        List<String> zones = Arrays.asList("中山", "信義", "仁愛", "中正", "安樂", "七堵", "暖暖");
        List<Sight> allSights = new ArrayList<>();

        for (String zone : zones) {
            List<Sight> zoneSights = getSightsByZone(zone);
            allSights.addAll(zoneSights);
        }

        return allSights;
    }
}
