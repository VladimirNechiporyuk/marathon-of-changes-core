package com.flamelab.marathonofchangescore.services.impl;

import com.flamelab.marathonofchangescore.entities.ExerciseWithTotalQuantity;
import com.flamelab.marathonofchangescore.exceptions.ExerciseNotFoundException;
import com.flamelab.marathonofchangescore.repositories.TotalQuantityOfExerciseRepository;
import com.flamelab.marathonofchangescore.services.TotalQuantityOfExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TotalQuantityOfExerciseServiceImpl implements TotalQuantityOfExerciseService {

    private TotalQuantityOfExerciseRepository totalQuantityOfExerciseRepository;

    @Autowired
    public TotalQuantityOfExerciseServiceImpl(TotalQuantityOfExerciseRepository totalQuantityOfExerciseRepository) {
        this.totalQuantityOfExerciseRepository = totalQuantityOfExerciseRepository;
    }

    @Override
    public void saveTotalQuantityOfExerciseList(Collection<ExerciseWithTotalQuantity> totalQuantityOfExerciseCollection) {
        totalQuantityOfExerciseRepository.saveAll(totalQuantityOfExerciseCollection);
    }

    @Override
    public List<ExerciseWithTotalQuantity> getAllByMarathonerId(ObjectId marathonerId) {
        List<ExerciseWithTotalQuantity> exerciseList = totalQuantityOfExerciseRepository.findAllByMarathonerId(marathonerId.toHexString());
        if (!exerciseList.isEmpty()) {
            return exerciseList;
        } else {
            log.warn("No completed exercises found for marathoner {}", marathonerId);
            throw new ExerciseNotFoundException(String.format("No completed exercises found for marathoner %s", marathonerId));
        }
    }

    @Override
    public ExerciseWithTotalQuantity getTotalQuantityByExerciseForMarathoner(ObjectId marathonerId, String exercise) {
        Optional<ExerciseWithTotalQuantity> optionalExerciseWithTotalQuantity = totalQuantityOfExerciseRepository.findByMarathonerIdAndExercise(marathonerId.toHexString(), exercise);
        if (optionalExerciseWithTotalQuantity.isPresent()) {
            return optionalExerciseWithTotalQuantity.get();
        } else {
            log.warn("No exercise with name {} for marathoner {}", exercise, marathonerId);
            throw new ExerciseNotFoundException(String.format("No exercise with name %s for marathoner %s", exercise, marathonerId));
        }
    }

    @Override
    public List<ExerciseWithTotalQuantity> getAllByExercise(String exercise) {
        List<ExerciseWithTotalQuantity> exerciseList = totalQuantityOfExerciseRepository.findAllByExercise(exercise);
        if (!exerciseList.isEmpty()) {
            return exerciseList;
        } else {
            log.warn("Nobody does not complete the exercise {}", exercise);
            throw new ExerciseNotFoundException(String.format("Nobody does not complete the exercise %s", exercise));
        }
    }
}
