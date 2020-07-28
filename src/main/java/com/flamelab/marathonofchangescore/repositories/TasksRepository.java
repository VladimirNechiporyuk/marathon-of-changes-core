package com.flamelab.marathonofchangescore.repositories;

import com.flamelab.marathonofchangescore.entities.Task;
import com.flamelab.marathonofchangescore.enums.TaskStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends MongoRepository<Task, ObjectId> {

    Optional<Task> findByName(String name);

    List<Task> findByMarathonerId(ObjectId marathonerId);

    List<Task> findByMarathonerIdAndTaskStatus(ObjectId id, TaskStatus taskStatus);

}
