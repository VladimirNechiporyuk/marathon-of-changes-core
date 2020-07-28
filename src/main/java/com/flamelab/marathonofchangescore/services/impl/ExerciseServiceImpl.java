package com.flamelab.marathonofchangescore.services.impl;

import com.flamelab.marathonofchangescore.dtos.forUpdating.UpdateExerciseDto;
import com.flamelab.marathonofchangescore.entities.Exercise;
import com.flamelab.marathonofchangescore.exceptions.ExerciseAlreadyExistsException;
import com.flamelab.marathonofchangescore.exceptions.ExerciseNotFoundException;
import com.flamelab.marathonofchangescore.repositories.ExerciseRepository;
import com.flamelab.marathonofchangescore.services.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExerciseServiceImpl implements ExerciseService {

    private ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Exercise createExercise(String name, long experience, String iterationType) {
        validationOnExistingExercise(name);
        Exercise exercise = Exercise.builder()
                .id(ObjectId.get())
                .name(name)
                .experience(experience)
                .iterationType(iterationType)
                .build();
        log.info("Creating exercise {}", exercise.toString());
        return exerciseRepository.save(exercise);
    }

    @Override
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        if (exercises.isEmpty()) {
            log.warn("There is no existing exercises");
            throw new ExerciseNotFoundException("There is no existing exercises");
        } else {
            return exercises;
        }
    }

    @Override
    public Exercise getById(ObjectId id) {
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        if (optionalExercise.isPresent()) {
            return optionalExercise.get();
        } else {
            log.warn("Exercise with id {} does not exists", id);
            throw new ExerciseNotFoundException(String.format("Exercise with name %s does not exists", id));
        }
    }

    @Override
    public Exercise getByName(String name) {
        Optional<Exercise> optionalExercise = exerciseRepository.findByName(name);
        if (optionalExercise.isPresent()) {
            return optionalExercise.get();
        } else {
            log.warn("Exercise with name {} does not exists", name);
            throw new ExerciseNotFoundException(String.format("Exercise with name %s does not exists", name));
        }
    }

    @Override
    public List<Exercise> getAllByExperience(long experience) {
        List<Exercise> exercises = exerciseRepository.findByExperience(experience);
        if (exercises.isEmpty()) {
            log.warn("There is no exercises with experience {}", experience);
            throw new ExerciseNotFoundException(String.format("There is no exercises with experience %s", experience));
        } else {
            return exercises;
        }
    }

    @Override
    public List<Exercise> getAllByIterationType(String iterationType) {
        List<Exercise> exercises = exerciseRepository.findByIterationType(iterationType);
        if (exercises.isEmpty()) {
            log.warn("There is no exercises with exerciseType {}", iterationType);
            throw new ExerciseNotFoundException(String.format("There is no exercises with exerciseType %s", iterationType));
        } else {
            return exercises;
        }
    }

    @Override
    public Exercise updateExercise(UpdateExerciseDto exerciseWithUpdates) {
        getById(exerciseWithUpdates.getExerciseId());
        return Exercise.builder()
                .id(exerciseWithUpdates.getExerciseId())
                .name(exerciseWithUpdates.getName())
                .experience(exerciseWithUpdates.getExperience())
                .iterationType(exerciseWithUpdates.getIterationType())
                .build();
    }

    @Override
    public boolean deleteExercise(ObjectId id) {
        getById(id);
        exerciseRepository.deleteById(id);
        log.info("Exercise with id {} was deleted", id);
        return true;
    }

    private void validationOnExistingExercise(String name) {
        Optional<Exercise> optionalExercise = exerciseRepository.findByName(name);
        if (optionalExercise.isPresent()) {
            log.warn("Exercise with name {} already existing", name);
            throw new ExerciseAlreadyExistsException(String.format("Exercise with name %s already existing", name));
        }
    }

}
