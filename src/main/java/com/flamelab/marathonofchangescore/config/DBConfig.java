package com.flamelab.marathonofchangescore.config;

import com.flamelab.marathonofchangescore.repositories.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = {
        MarathonersRepository.class,
        TasksRepository.class,
        ExerciseRepository.class,
        LevelsRepository.class,
        TotalQuantityOfExerciseRepository.class,
        SettingsRepository.class})
@Configuration
public class DBConfig {
}
