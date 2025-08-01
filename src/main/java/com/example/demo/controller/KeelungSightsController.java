package com.example.demo.controller;

import com.example.demo.model.Sight;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class KeelungSightsController {
    @Autowired
    private KeelungSightsRepository keelungSightsRepository;
    //新增
    @PostMapping("/keelung/sights")
    public ResponseEntity<Void> addKeelungSights(@RequestBody Sight sight){
        sight.setId(null);
        keelungSightsRepository.insert(sight);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build(Map.of("id", sight.getId()));

        return ResponseEntity.created(uri).build();
    }
    //取得
    @GetMapping("/keelung/sights/{id}")
    public ResponseEntity<Sight> getKeelungSight(@PathVariable String id) {
        Optional<Sight> sightOp = KeelungSightsRepository.findById(id);
        return sightOp.isPresent()
                ? ResponseEntity.ok(sightOp.get())
                : ResponseEntity.notFound().build();
    }
    //取得多筆
    @GetMapping("/keelung/sights/ids")
    public ResponseEntity<List<Sight>> getKeelungSights(@RequestParam List<String> idlist){
        List<Sight> sights = keelungSightsRepository.findAllById(idlist);
        return ResponseEntity.ok(sights);
    }
    //修改
    @PutMapping("/keelung/sights/{id}")
    public ResponseEntity<Void> updateKeelungSight(
            @PathVariable String id, @RequestBody Sight request
    ) {
        Optional<Sight> sightOp = KeelungSightsRepository.findById(id);
        if (sightOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sight sight = sightOp.get();
        sight.setSightName(request.getSightName());
        sight.setZone(request.getZone());
        sight.setCategory(request.getCategory());
        sight.setPhotoURL(request.getPhotoURL());
        sight.setDescription(request.getDescription());
        sight.setAddress(request.getAddress());
        keelungSightsRepository.save(sight);

        return ResponseEntity.noContent().build();
    }
    //刪除
    @DeleteMapping("/keelung/sights/{id}")
    public ResponseEntity<Void> deleteKeelungSight(@PathVariable String id) {
        keelungSightsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
