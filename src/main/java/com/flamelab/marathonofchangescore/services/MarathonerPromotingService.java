package com.flamelab.marathonofchangescore.services;

import com.flamelab.marathonofchangescore.dtos.MarathonerDto;
import com.flamelab.marathonofchangescore.utiles.DataForUpdateMarathonerAfterCompletingTheExercises;

public interface MarathonerPromotingService {

    MarathonerDto updateExperienceAfterCompletingTheExercisesAndTasks(DataForUpdateMarathonerAfterCompletingTheExercises dataAfterTheTask);

}
