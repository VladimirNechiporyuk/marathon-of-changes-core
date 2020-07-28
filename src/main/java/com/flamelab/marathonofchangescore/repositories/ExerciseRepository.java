package com.flamelab.marathonofchangescore.repositories;

import com.flamelab.marathonofchangescore.entities.Exercise;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends MongoRepository<Exercise, ObjectId> {

    Optional<Exercise> findByName(String name);

    List<Exercise> findByExperience(long experience);

    List<Exercise> findByIterationType(String iterationType);

}
