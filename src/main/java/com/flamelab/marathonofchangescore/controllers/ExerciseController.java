package com.flamelab.marathonofchangescore.controllers;

import com.flamelab.marathonofchangescore.dtos.forCreating.CreateExerciseDto;
import com.flamelab.marathonofchangescore.dtos.forUpdating.UpdateExerciseDto;
import com.flamelab.marathonofchangescore.entities.Exercise;
import com.flamelab.marathonofchangescore.enums.IterationType;
import com.flamelab.marathonofchangescore.services.ExerciseService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody CreateExerciseDto createExerciseDto) {
        return ResponseEntity.ok(
                exerciseService.createExercise(
                        createExerciseDto.getName(),
                        createExerciseDto.getExperience(),
                        createExerciseDto.getIterationType()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Exercise>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable("id") ObjectId id) {
        return ResponseEntity.ok(exerciseService.getById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<Exercise> getExerciseByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(exerciseService.getByName(name));
    }

    @GetMapping("/experience")
    public ResponseEntity<List<Exercise>> findByExperience(@RequestParam("experience") long experience) {
        return ResponseEntity.ok(exerciseService.getAllByExperience(experience));
    }

    @GetMapping("/iterationType")
    public ResponseEntity<List<Exercise>> findByExerciseType(@RequestParam("iterationType") String iterationType) {
        return ResponseEntity.ok(exerciseService.getAllByIterationType(iterationType));
    }

    @GetMapping("/info/iterationTypes")
    public ResponseEntity<List<IterationType>> getAllIterationTypes() {
        return ResponseEntity.ok(Arrays.asList(IterationType.values()));
    }

    @PutMapping
    public ResponseEntity<Exercise> updateExercise(@RequestBody UpdateExerciseDto exercise) {
        return ResponseEntity.ok(exerciseService.updateExercise(exercise));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteExercise(@RequestParam("id") ObjectId id) {
        return ResponseEntity.ok(exerciseService.deleteExercise(id));
    }

}
