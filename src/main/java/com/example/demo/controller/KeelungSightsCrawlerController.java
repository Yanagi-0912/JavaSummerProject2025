package com.example.demo.controller;

import com.example.demo.model.KeelungSightsCrawler;
import com.example.demo.model.Sight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeelungSightsCrawlerController {
    @GetMapping("/{zone}")
    public Sight[] getSights(@PathVariable("zone") String zone) {
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();
        Sight[] sights = crawler.getItems(zone);
        if (sights == null) {
            return new Sight[0];
        }
        return sights;
    }
}

//http://localhost:8080/七堵
