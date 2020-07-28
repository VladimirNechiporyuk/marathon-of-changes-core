package com.flamelab.marathonofchangescore.services;

import com.flamelab.marathonofchangescore.entities.Level;

import java.util.List;

public interface LevelService {

    List<Level> createLevels(int amountOfLevels, long stepOfExperienceAdding);

    List<Level> getAllLevels();

    Level getLevelByNumber(int number);

    Level getLevelByExperienceValue(long experienceValue);

    List<Level> updateExperienceValueForAllLevels(long experienceValue);

    Integer getExperienceStep();

}
