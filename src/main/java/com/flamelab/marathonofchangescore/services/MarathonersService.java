package com.flamelab.marathonofchangescore.services;

import com.flamelab.marathonofchangescore.dtos.MarathonerDto;
import com.flamelab.marathonofchangescore.dtos.forUpdating.UpdateMarathonerDto;
import com.flamelab.marathonofchangescore.entities.ExerciseWithTotalQuantity;
import org.bson.types.ObjectId;

import java.util.List;

public interface MarathonersService {

    MarathonerDto createMarathoner(String marathonerName);

    List<MarathonerDto> getAllMarathoners();

    MarathonerDto getMarathonerById(ObjectId id);

    MarathonerDto getMarathonerByName(String name);

    List<MarathonerDto> getMarathonersByLevel(int level);

    ExerciseWithTotalQuantity getExerciseWithTotalQuantityForMarathoner(ObjectId marathonerId, String exercise);

    List<ExerciseWithTotalQuantity> getAllExercisesWithTotalQuantityForMarathoner(ObjectId marathonerId);

    MarathonerDto updateMarathoner(UpdateMarathonerDto marathoner);

    boolean deleteMarathoner(ObjectId id);

}
