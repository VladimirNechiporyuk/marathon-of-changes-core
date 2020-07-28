package com.flamelab.marathonofchangescore.services;

import com.flamelab.marathonofchangescore.dtos.TaskDto;
import com.flamelab.marathonofchangescore.dtos.forCreating.CreateTaskDto;
import com.flamelab.marathonofchangescore.enums.TaskStatus;
import org.bson.types.ObjectId;

import java.util.List;

public interface TasksService {

    TaskDto createTask(CreateTaskDto createTaskDto);

    List<TaskDto> getAllTasks();

    TaskDto getTaskById(ObjectId taskId);

    TaskDto getTaskByName(String name);

    List<TaskDto> getAllTaskByMarathonerId(ObjectId marathonerId);

    List<TaskDto> getTasksByMarathonerIdAndTaskStatus(ObjectId marathonerId, TaskStatus taskStatus);

    List<TaskDto> getTasksByMarathonerIdAndExerciseName(ObjectId marathonerId, String taskName);

    TaskDto updateTask(TaskDto task);

    boolean deleteTask(ObjectId taskId);

}
