package com.flamelab.marathonofchangescore.controllers;


import com.flamelab.marathonofchangescore.entities.Level;
import com.flamelab.marathonofchangescore.services.LevelService;
import com.flamelab.marathonofchangescore.utiles.SettingNames;
import com.flamelab.marathonofchangescore.utiles.SettingsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/levels")
public class LevelController {

    private LevelService levelService;
    private SettingsUtils settingsUtils;
    private SettingNames settingNames;

    @Autowired
    public LevelController(LevelService levelService,
                           SettingsUtils settingsUtils,
                           SettingNames settingNames) {
        this.levelService = levelService;
        this.settingsUtils = settingsUtils;
        this.settingNames = settingNames;
    }

    @PostMapping
    public ResponseEntity<List<Level>> createLevel(@RequestParam("amountOfLevels") int amountOfLevels) {
        return ResponseEntity.ok(levelService.createLevels(amountOfLevels,
                Long.valueOf(settingsUtils.getSettingByName(settingNames.experienceStep).getValue())));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Level>> getAllLevels() {
        return ResponseEntity.ok(levelService.getAllLevels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Level> getLevelById(@PathVariable int id) {
        return ResponseEntity.ok(levelService.getLevelByNumber(id));
    }

    @GetMapping("/experienceValue")
    public ResponseEntity<Level> getLevelByExperienceValue(@RequestParam("experienceValue") long experienceValue) {
        return ResponseEntity.ok(levelService.getLevelByExperienceValue(experienceValue));
    }

    @GetMapping
    public ResponseEntity<Integer> getStepBetweenLevels() {
        return ResponseEntity.ok(levelService.getExperienceStep());
    }

    @PutMapping("/updateExperienceValue")
    public ResponseEntity<List<Level>> updateExperienceValueForLevel(long experienceValue) {
        return ResponseEntity.ok(levelService.updateExperienceValueForAllLevels(experienceValue));
    }


}
