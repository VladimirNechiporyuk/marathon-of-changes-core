package com.flamelab.marathonofchangescore.services;

import com.flamelab.marathonofchangescore.entities.ExerciseWithTotalQuantity;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.List;

public interface TotalQuantityOfExerciseService {

    void saveTotalQuantityOfExerciseList(Collection<ExerciseWithTotalQuantity> totalQuantityOfExerciseCollection);

    List<ExerciseWithTotalQuantity> getAllByMarathonerId(ObjectId marathonerId);

    ExerciseWithTotalQuantity getTotalQuantityByExerciseForMarathoner(ObjectId marathonerId, String exercise);

    List<ExerciseWithTotalQuantity> getAllByExercise(String exercise);

}
