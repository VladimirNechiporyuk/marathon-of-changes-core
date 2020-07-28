package com.flamelab.marathonofchangescore.repositories;

import com.flamelab.marathonofchangescore.entities.Level;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LevelsRepository extends MongoRepository<Level, ObjectId> {

    Optional<Level> findById(int id);

    Optional<Level> findByExperienceValue(long experienceValue);

}
