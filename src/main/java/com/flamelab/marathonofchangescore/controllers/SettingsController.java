package com.flamelab.marathonofchangescore.controllers;

import com.flamelab.marathonofchangescore.dtos.forCreating.CreateSettingDto;
import com.flamelab.marathonofchangescore.entities.Setting;
import com.flamelab.marathonofchangescore.utiles.SettingsUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private SettingsUtils settingsUtils;

    @Autowired
    public SettingsController(SettingsUtils settingsUtils) {
        this.settingsUtils = settingsUtils;
    }

    @PostMapping
    public ResponseEntity<Setting> createSetting(@RequestBody CreateSettingDto createSettingDto) {
        return ResponseEntity.ok(settingsUtils.createSetting(createSettingDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Setting>> getAllSettings() {
        return ResponseEntity.ok(settingsUtils.getAllSettings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setting> getSettingById(@PathVariable("id") ObjectId id) {
        return ResponseEntity.ok(settingsUtils.getSettingById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<Setting> getSettingByName(@RequestParam String name) {
        return ResponseEntity.ok(settingsUtils.getSettingByName(name));
    }

    @PutMapping
    public ResponseEntity<Setting> updateSetting(@RequestParam String name,
                                                 @RequestBody CreateSettingDto updateSettingDto) {
        return ResponseEntity.ok(settingsUtils.updateSetting(name, updateSettingDto));
    }

    @DeleteMapping("/byId/{id}")
    public ResponseEntity<Boolean> deleteSetting(@PathVariable("id") ObjectId id) {
        return ResponseEntity.ok(settingsUtils.deleteSetting(id));
    }

    @DeleteMapping("/byName")
    public ResponseEntity<Boolean> deleteSettingByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(settingsUtils.deleteSettingByName(name));
    }

}
