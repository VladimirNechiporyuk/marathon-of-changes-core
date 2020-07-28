package com.flamelab.marathonofchangescore.repositories;

import com.flamelab.marathonofchangescore.entities.Marathoner;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MarathonersRepository extends MongoRepository<Marathoner, ObjectId> {

    Optional<Marathoner> findByName(String name);

    List<Marathoner> findByLevel(int level);

    List<Marathoner> findByExperience(long experience);

}
