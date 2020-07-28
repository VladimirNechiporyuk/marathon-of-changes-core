package com.flamelab.marathonofchangescore.services.impl;

import com.flamelab.marathonofchangescore.dtos.MarathonerDto;
import com.flamelab.marathonofchangescore.dtos.forUpdating.UpdateMarathonerDto;
import com.flamelab.marathonofchangescore.entities.ExerciseWithTotalQuantity;
import com.flamelab.marathonofchangescore.entities.Marathoner;
import com.flamelab.marathonofchangescore.exceptions.ExerciseNotFoundException;
import com.flamelab.marathonofchangescore.exceptions.MarathonerAlreadyExistsException;
import com.flamelab.marathonofchangescore.exceptions.MarathonerNotFoundException;
import com.flamelab.marathonofchangescore.repositories.MarathonersRepository;
import com.flamelab.marathonofchangescore.services.MarathonersService;
import com.flamelab.marathonofchangescore.services.TotalQuantityOfExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarathonersServiceImpl implements MarathonersService {

    private MarathonersRepository marathonersRepository;
    private TotalQuantityOfExerciseService totalQuantityOfExerciseService;

    @Autowired
    public MarathonersServiceImpl(MarathonersRepository marathonersRepository,
                                  TotalQuantityOfExerciseService totalQuantityOfExerciseService) {
        this.marathonersRepository = marathonersRepository;
        this.totalQuantityOfExerciseService = totalQuantityOfExerciseService;
    }

    @Override
    public MarathonerDto createMarathoner(String name) {
        validationOnExistingMarathoner(name);
        Marathoner marathoner = Marathoner.builder()
                .id(ObjectId.get())
                .name(name)
                .level(0)
                .experience(0)
                .tasks(new ArrayList<>())
                .build();
        log.info("Creating marathoner {}", marathoner.toString());
        return marathonersRepository.save(marathoner).toDto();
    }

    @Override
    public List<MarathonerDto> getAllMarathoners() {
        List<Marathoner> marathoners = marathonersRepository.findAll();
        if (marathoners.isEmpty()) {
            log.warn("There is no existing marathoners");
            throw new MarathonerNotFoundException("There is no existing marathoners");
        } else {
            return marathoners.stream()
                    .map(Marathoner::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public MarathonerDto getMarathonerById(ObjectId id) {
        Optional<Marathoner> marathoner = marathonersRepository.findById(id);
        if (marathoner.isPresent()) {
            return marathoner.get().toDto();
        } else {
            log.warn("Marathoner with id {} does not exists", id);
            throw new MarathonerNotFoundException(String.format("Marathoner with id %s does not exists", id));
        }
    }

    @Override
    public MarathonerDto getMarathonerByName(String name) {
        Optional<Marathoner> marathoner = marathonersRepository.findByName(name);
        if (marathoner.isPresent()) {
            return marathoner.get().toDto();
        } else {
            log.warn("Marathoner with id {} does not exist", name);
            throw new MarathonerNotFoundException(String.format("Marathoner with id %s does not exists", name));
        }
    }

    @Override
    public List<MarathonerDto> getMarathonersByLevel(int level) {
        List<Marathoner> marathoners = marathonersRepository.findByLevel(level);
        if (marathoners.isEmpty()) {
            log.warn("There is no marathoners with level {}", level);
            throw new MarathonerNotFoundException(String.format("There is no marathoners with level %s", level));
        } else {
            return marathoners.stream()
                    .map(Marathoner::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public ExerciseWithTotalQuantity getExerciseWithTotalQuantityForMarathoner(ObjectId marathonerId, String exercise) {
        return totalQuantityOfExerciseService.getTotalQuantityByExerciseForMarathoner(marathonerId, exercise);
    }


    @Override
    public List<ExerciseWithTotalQuantity> getAllExercisesWithTotalQuantityForMarathoner(ObjectId marathonerId) {
        List<ExerciseWithTotalQuantity> allExecutedExercises = totalQuantityOfExerciseService.getAllByMarathonerId(marathonerId);
        if (allExecutedExercises.isEmpty()) {
            log.warn("Marathoner with id {} didn't execute any exercises");
            throw new ExerciseNotFoundException(String.format("Marathoner with id %s didn't execute any exercises", marathonerId.toHexString()));
        } else {
            return allExecutedExercises;
        }
    }

    @Override
    public MarathonerDto updateMarathoner(UpdateMarathonerDto marathonerWithUpdates) {
        MarathonerDto marathoner = getMarathonerById(marathonerWithUpdates.getId());
        if (!marathoner.getName().equals(marathonerWithUpdates.getName())) {
            validationOnExistingMarathoner(marathonerWithUpdates.getName());
        }
        marathoner.setName(marathonerWithUpdates.getName());
        marathoner.setLevel(marathonerWithUpdates.getLevel());
        marathoner.setExperience(marathonerWithUpdates.getExperience());
        marathoner.setTasks(marathonerWithUpdates.getTasks());
        return marathonersRepository.save(marathoner.toEntity()).toDto();
    }

    @Override
    public boolean deleteMarathoner(ObjectId id) {
        MarathonerDto marathoner = getMarathonerById(id);
        marathonersRepository.delete(marathoner.toEntity());
        log.info("Marathoner with id {} was deleted", id);
        return true;
    }

    private void validationOnExistingMarathoner(String name) {
        Optional<Marathoner> optionalMarathoner = marathonersRepository.findByName(name);
        if (optionalMarathoner.isPresent()) {
            log.warn("Marathoner with name {} already exists", name);
            throw new MarathonerAlreadyExistsException(String.format("Marathoner with name %s already exists", name));
        }
    }
}
