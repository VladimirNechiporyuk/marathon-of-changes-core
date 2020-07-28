package com.flamelab.marathonofchangescore.repositories;

import com.flamelab.marathonofchangescore.entities.Setting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SettingsRepository extends MongoRepository<Setting, ObjectId> {

    Optional<Setting> findByName(String name);

}
