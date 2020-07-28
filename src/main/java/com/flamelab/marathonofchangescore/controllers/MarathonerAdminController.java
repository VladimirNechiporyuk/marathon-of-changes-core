package com.flamelab.marathonofchangescore.controllers;

import com.flamelab.marathonofchangescore.dtos.MarathonerDto;
import com.flamelab.marathonofchangescore.dtos.forUpdating.UpdateMarathonerDto;
import com.flamelab.marathonofchangescore.entities.ExerciseWithTotalQuantity;
import com.flamelab.marathonofchangescore.services.MarathonersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marathoners")
public class MarathonerAdminController {

    private MarathonersService marathonersService;

    @Autowired
    public MarathonerAdminController(MarathonersService marathonersService) {
        this.marathonersService = marathonersService;
    }

    @PostMapping
    public ResponseEntity<MarathonerDto> createMarathoner(@RequestParam("name") String name) {
        return ResponseEntity.ok(marathonersService.createMarathoner(name));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MarathonerDto>> getAllMarathoners() {
        return ResponseEntity.ok(marathonersService.getAllMarathoners());
    }

    @GetMapping("/id")
    public ResponseEntity<MarathonerDto> getMarathoner(@RequestParam("id") ObjectId id) {
        return ResponseEntity.ok(marathonersService.getMarathonerById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<MarathonerDto> getMarathonerByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(marathonersService.getMarathonerByName(name));
    }

    @GetMapping("/level")
    public ResponseEntity<List<MarathonerDto>> getMarathonerByLevel(@RequestParam("level") int level) {
        return ResponseEntity.ok(marathonersService.getMarathonersByLevel(level));
    }

    @GetMapping("/totalQuantityOfExercise")
    public ResponseEntity<ExerciseWithTotalQuantity> getTotalQuantityOfExercise(@RequestParam("marathonerId") ObjectId marathonerId,
                                                                                @RequestParam("exerciseName") String exerciseName) {
        return ResponseEntity.ok(marathonersService.getExerciseWithTotalQuantityForMarathoner(marathonerId, exerciseName));
    }

    @GetMapping("/allTotalQuantityOfExercise")
    public ResponseEntity<List<ExerciseWithTotalQuantity>> getAllTotalQuantityOfExercise(@RequestParam("marathonerId") ObjectId marathonerId) {
        return ResponseEntity.ok(marathonersService.getAllExercisesWithTotalQuantityForMarathoner(marathonerId));
    }

    @PutMapping
    public ResponseEntity<MarathonerDto> updateMarathoner(@RequestBody UpdateMarathonerDto marathoner) {
        return ResponseEntity.ok(marathonersService.updateMarathoner(marathoner));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteMarathoner(@RequestParam("id") ObjectId id) {
        return ResponseEntity.ok(marathonersService.deleteMarathoner(id));
    }

}
