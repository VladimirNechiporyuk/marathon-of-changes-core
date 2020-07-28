package com.flamelab.marathonofchangescore.controllers;

import com.flamelab.marathonofchangescore.dtos.TaskDto;
import com.flamelab.marathonofchangescore.dtos.forCreating.CreateTaskDto;
import com.flamelab.marathonofchangescore.enums.TaskStatus;
import com.flamelab.marathonofchangescore.services.TasksService;
import com.flamelab.marathonofchangescore.services.impl.TasksServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private TasksService tasksService;

    @Autowired
    public TasksController(TasksServiceImpl tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto createTaskDto) {
        return ResponseEntity.ok(tasksService.createTask(createTaskDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(tasksService.getAllTasks());
    }

    @GetMapping("/id")
    public ResponseEntity<TaskDto> getTaskById(@RequestParam("id") ObjectId id) {
        return ResponseEntity.ok(tasksService.getTaskById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<TaskDto> getTaskByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(tasksService.getTaskByName(name));
    }

    @GetMapping("/executor")
    public ResponseEntity<List<TaskDto>> getTaskByExecutor(@RequestParam("marathonerId") ObjectId marathonerId) {
        return ResponseEntity.ok(tasksService.getAllTaskByMarathonerId(marathonerId));
    }

    @GetMapping("/statusAndExecutor")
    public ResponseEntity<List<TaskDto>> getTaskByTaskStatusAndMarathonerId(@RequestParam("marathonerId") ObjectId id,
                                                                            @RequestParam("taskStatus") TaskStatus taskStatus) {
        return ResponseEntity.ok(tasksService.getTasksByMarathonerIdAndTaskStatus(id, taskStatus));
    }

    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto task) {
        return ResponseEntity.ok(tasksService.updateTask(task));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteTaskById(@RequestParam ObjectId id) {
        return ResponseEntity.ok(tasksService.deleteTask(id));
    }

}
