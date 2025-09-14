package com.example.demo.controller;

import com.example.demo.model.Sight;
import com.example.demo.service.KeelungSightsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/sights")
@CrossOrigin(origins = "*")
public class SightsController {
    private static final Logger logger = LoggerFactory.getLogger(SightsController.class);

    @Autowired
    private KeelungSightsService keelungSightsService;

    @GetMapping("/keelung")
    public ResponseEntity<List<Sight>> getSightsByZone(@RequestParam String zone) {
        try {
            logger.info("Received request for zone: {}", zone);
            List<Sight> sights = keelungSightsService.getSightsByZone(zone);
            if (sights.isEmpty()) {
                logger.warn("No sights found for zone: {}", zone);
                return ResponseEntity.notFound().build();
            }
            logger.info("Found {} sights for zone: {}", sights.size(), zone);
            return ResponseEntity.ok(sights);
        } catch (Exception e) {
            logger.error("Error fetching sights for zone {}: {}", zone, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/keelung/all")
    public ResponseEntity<List<Sight>> getAllSights() {
        try {
            List<Sight> sights = keelungSightsService.getAllSights();
            if (sights.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(sights);
        } catch (Exception e) {
            logger.error("Error fetching all sights: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
