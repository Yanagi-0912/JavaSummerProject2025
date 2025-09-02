package com.example.demo.controller;

import com.example.demo.model.Sight;
import com.example.demo.service.KeelungSightsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/sights")
public class SightsController {
    @Autowired
    private KeelungSightsService keelungSightsService;

    @GetMapping("/keelung/{zone}")
    public ResponseEntity<List<Sight>> getSightsByZone(@PathVariable String zone) {
        List<Sight> sights = keelungSightsService.getSightsByZone(zone);
        if (sights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sights);
    }
}
