package com.flamelab.marathonofchangescore.services;

import com.flamelab.marathonofchangescore.dtos.forUpdating.UpdateExerciseDto;
import com.flamelab.marathonofchangescore.entities.Exercise;
import org.bson.types.ObjectId;

import java.util.List;

public interface ExerciseService {

    Exercise createExercise(String name, long experience, String iterationType);

    List<Exercise> getAllExercises();

    Exercise getById(ObjectId id);

    Exercise getByName(String name);

    List<Exercise> getAllByExperience(long experience);

    List<Exercise> getAllByIterationType(String exerciseType);

    Exercise updateExercise(UpdateExerciseDto exercise);

    boolean deleteExercise(ObjectId id);

}
