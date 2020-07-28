package com.flamelab.marathonofchangescore.services.impl;

import com.flamelab.marathonofchangescore.dtos.MarathonerDto;
import com.flamelab.marathonofchangescore.dtos.TaskDto;
import com.flamelab.marathonofchangescore.dtos.forCreating.CreateTaskDto;
import com.flamelab.marathonofchangescore.dtos.forUpdating.UpdateMarathonerDto;
import com.flamelab.marathonofchangescore.entities.Task;
import com.flamelab.marathonofchangescore.enums.TaskStatus;
import com.flamelab.marathonofchangescore.enums.TaskType;
import com.flamelab.marathonofchangescore.exceptions.MarathonerAlreadyExistsException;
import com.flamelab.marathonofchangescore.exceptions.TaskNotFoundException;
import com.flamelab.marathonofchangescore.repositories.TasksRepository;
import com.flamelab.marathonofchangescore.services.MarathonersService;
import com.flamelab.marathonofchangescore.services.TasksService;
import com.flamelab.marathonofchangescore.utiles.CompletedExerciseData;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.flamelab.marathonofchangescore.enums.TaskStatus.NEW;

@Service
@Slf4j
public class TasksServiceImpl implements TasksService {

    private TasksRepository tasksRepository;
    private MarathonersService marathonersService;

    @Autowired
    public TasksServiceImpl(TasksRepository tasksRepository,
                            MarathonersService marathonersService) {
        this.tasksRepository = tasksRepository;
        this.marathonersService = marathonersService;
    }

    @Override
    public TaskDto createTask(CreateTaskDto createTaskDto) {
        validationOnExistingTask(createTaskDto.getTaskName());
        createTaskDto.getMarathonerExerciseDataWithGoalList().forEach(exercise -> exercise.setCompletedQuantity(0));
        Task task = Task.builder()
                .id(ObjectId.get())
                .name(createTaskDto.getTaskName())
                .experience(createTaskDto.getTaskExperience())
                .marathonerId(createTaskDto.getMarathonerId())
                .taskStatus(NEW)
                .taskType(TaskType.valueOf(createTaskDto.getTaskType()))
                .marathonerExerciseDataWithGoalList(createTaskDto.getMarathonerExerciseDataWithGoalList())
                .createdDate(LocalDateTime.now())
                .build();
        addTaskToMarathoner(task.getId(), createTaskDto.getMarathonerId());
        log.info("Creating task {}", task.toString());
        return tasksRepository.save(task).toDto();
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = tasksRepository.findAll();
        if (tasks.isEmpty()) {
            log.error("There is no tasks at all");
            throw new TaskNotFoundException("There is no tasks at all");
        } else {
            return tasks.stream()
                    .map(Task::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public TaskDto getTaskById(ObjectId id) {
        Optional<Task> task = tasksRepository.findById(id);
        if (task.isPresent()) {
            return task.get().toDto();
        } else {
            log.error("Task with id {} does not exist", id);
            throw new TaskNotFoundException(String.format("Task with id %s does not exist", id));
        }
    }

    @Override
    public TaskDto getTaskByName(String name) {
        Optional<Task> task = tasksRepository.findByName(name);
        if (task.isPresent()) {
            return task.get().toDto();
        } else {
            log.error("Task with id {} does not exist", name);
            throw new TaskNotFoundException(String.format("Task with id %s does not exist", name));
        }
    }

    @Override
    public List<TaskDto> getAllTaskByMarathonerId(ObjectId marathonerId) {
        List<Task> tasks = tasksRepository.findByMarathonerId(marathonerId);
        if (tasks.isEmpty()) {
            log.error("There is no tasks for marathonerId {}", marathonerId);
            throw new TaskNotFoundException(String.format("There is no tasks for marathonerId %s", marathonerId));
        } else {
            return tasks.stream()
                    .map(Task::toDto)
                    .collect(Collectors.toList());
        }
    }


    @Override
    public List<TaskDto> getTasksByMarathonerIdAndTaskStatus(ObjectId marathonerId, TaskStatus taskStatus) {
        List<Task> tasks = tasksRepository.findByMarathonerIdAndTaskStatus(marathonerId, taskStatus);
        if (tasks.isEmpty()) {
            log.warn("There is no tasks for marathoner {} with status {}", marathonerId, taskStatus);
            throw new TaskNotFoundException(String.format("There is no tasks for marathoner %s with status %s", marathonerId, taskStatus));
        }
        return tasks.stream()
                .map(Task::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByMarathonerIdAndExerciseName(ObjectId marathonerId, String exerciseName) {
        return getAllTaskByMarathonerId(marathonerId).stream()
                .filter(task -> findAllTasksByExerciseName(task, exerciseName).isPresent())
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(TaskDto taskWithUpdates) {
        TaskDto oldTaskRecord = getTaskById(new ObjectId(taskWithUpdates.getId()));
        if (!oldTaskRecord.getName().equals(taskWithUpdates.getName())) {
            validationOnExistingTask(taskWithUpdates.getName());
        }
        Task task = taskWithUpdates.toEntity();
        task.setId(new ObjectId(taskWithUpdates.getId()));
        return tasksRepository.save(task).toDto();
    }

    @Override
    public boolean deleteTask(ObjectId id) {
        getTaskById(id);
        tasksRepository.deleteById(id);
        log.info("Task with id {} was deleted", id);
        return true;
    }

    private void validationOnExistingTask(String name) {
        Optional<Task> optionalTask = tasksRepository.findByName(name);
        if (optionalTask.isPresent()) {
            log.info("Task with name {} already exists", name);
            throw new MarathonerAlreadyExistsException(String.format("Task with name %s already exists", name));
        }
    }

    private void addTaskToMarathoner(ObjectId taskId, ObjectId marathonerId) {
        MarathonerDto marathoner = marathonersService.getMarathonerById(marathonerId);
        marathoner.getTasks().add(taskId);
        marathonersService.updateMarathoner(new UpdateMarathonerDto(
                new ObjectId(marathoner.getId()), marathoner.getName(), marathoner.getLevel(), marathoner.getExperience(), marathoner.getTasks()));
    }

    private Optional<TaskDto> findAllTasksByExerciseName(TaskDto task, String exerciseName) {
        for (CompletedExerciseData exerciseData : task.getMarathonerExerciseDataWithGoalList()) {
            if (exerciseData.getExerciseName().equals(exerciseName)) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }
}
