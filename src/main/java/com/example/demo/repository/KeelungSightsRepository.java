package com.example.demo.repository;

import com.example.demo.model.Sight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeelungSightsRepository extends MongoRepository<Sight, String> {
}
