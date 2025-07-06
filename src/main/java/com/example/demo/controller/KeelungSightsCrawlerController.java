package com.example.demo.controller;

import com.example.demo.model.KeelungSightsCrawler;
import com.example.demo.model.Sight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class KeelungSightsCrawlerController {
    @GetMapping("sight/{zone}")
    public ResponseEntity<Sight[]> getSights(@PathVariable("zone") String zone) {
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();
        Sight[] sights = crawler.getItems(zone);
        if (sights == null || sights.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(sights);
    }
}

//http://localhost:8080/sight/七堵
