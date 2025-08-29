package com.example.demo.repository;

import com.example.demo.model.Sight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SightsRepository extends MongoRepository<Sight, String> {
    @Query("{ 'Zone': ?0 }")
    List<Sight> findByZone(String zone);
}
