package com.flamelab.marathonofchangescore.controllers;

import com.flamelab.marathonofchangescore.dtos.MarathonerDto;
import com.flamelab.marathonofchangescore.services.MarathonerPromotingService;
import com.flamelab.marathonofchangescore.utiles.DataForUpdateMarathonerAfterCompletingTheExercises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marathonOfChanges")
public class MarathonerPromotingController {

    private MarathonerPromotingService marathonerPromotingService;

    @Autowired
    public MarathonerPromotingController(MarathonerPromotingService marathonerPromotingService) {
        this.marathonerPromotingService = marathonerPromotingService;
    }

    @PutMapping("/giveExperience")
    public ResponseEntity<MarathonerDto> giveExperienceForCompletingExercises(@RequestBody DataForUpdateMarathonerAfterCompletingTheExercises data) {
        return ResponseEntity.ok(marathonerPromotingService.updateExperienceAfterCompletingTheExercisesAndTasks(data));
    }

}
