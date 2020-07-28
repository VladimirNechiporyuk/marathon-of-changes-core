package com.flamelab.marathonofchangescore.repositories;

import com.flamelab.marathonofchangescore.entities.ExerciseWithTotalQuantity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TotalQuantityOfExerciseRepository extends MongoRepository<ExerciseWithTotalQuantity, ObjectId> {

    List<ExerciseWithTotalQuantity> findAllByMarathonerId(String marathonerId);

    Optional<ExerciseWithTotalQuantity> findByMarathonerIdAndExercise(String marathonerId, String exercise);

    List<ExerciseWithTotalQuantity> findAllByExercise(String exercise);

}
