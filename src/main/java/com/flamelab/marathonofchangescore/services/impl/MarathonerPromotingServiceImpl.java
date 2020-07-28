package com.flamelab.marathonofchangescore.services.impl;

import com.flamelab.marathonofchangescore.dtos.MarathonerDto;
import com.flamelab.marathonofchangescore.dtos.TaskDto;
import com.flamelab.marathonofchangescore.dtos.forUpdating.UpdateMarathonerDto;
import com.flamelab.marathonofchangescore.entities.Exercise;
import com.flamelab.marathonofchangescore.entities.ExerciseWithTotalQuantity;
import com.flamelab.marathonofchangescore.entities.Level;
import com.flamelab.marathonofchangescore.services.*;
import com.flamelab.marathonofchangescore.utiles.DataForUpdateMarathonerAfterCompletingTheExercises;
import com.flamelab.marathonofchangescore.utiles.CompletedExerciseData;
import com.flamelab.marathonofchangescore.utiles.CompletedExerciseDataWithGoal;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.flamelab.marathonofchangescore.enums.TaskStatus.DONE;
import static com.flamelab.marathonofchangescore.enums.TaskStatus.IN_PROGRESS;

@Service
@Slf4j
public class MarathonerPromotingServiceImpl implements MarathonerPromotingService {

    private ExerciseService exerciseService;
    private LevelService levelService;
    private MarathonersService marathonersService;
    private TasksService tasksService;
    private TotalQuantityOfExerciseService totalQuantityOfExerciseService;

    @Autowired
    public MarathonerPromotingServiceImpl(ExerciseService exerciseService,
                                          LevelService levelService,
                                          MarathonersService marathonersService,
                                          TasksService tasksService,
                                          TotalQuantityOfExerciseService totalQuantityOfExerciseService) {
        this.exerciseService = exerciseService;
        this.levelService = levelService;
        this.marathonersService = marathonersService;
        this.tasksService = tasksService;
        this.totalQuantityOfExerciseService = totalQuantityOfExerciseService;
    }

    @Override
    public MarathonerDto updateExperienceAfterCompletingTheExercisesAndTasks(DataForUpdateMarathonerAfterCompletingTheExercises completedExercisesData) {
        log.info("Updating experience and level for marathoner {}", completedExercisesData.getMarathonerId());
        MarathonerDto marathoner = marathonersService.getMarathonerById(completedExercisesData.getMarathonerId());
        updateExperienceAfterCompletingExercises(marathoner, completedExercisesData.getCompletedExercises());
        updateExperienceAfterCompletingTasks(marathoner, completedExercisesData);
        updateMarathonerLevelIfNeeded(marathoner);
        updateTotalQuantityOfExercise(completedExercisesData);
        return marathoner;
    }

    private void updateExperienceAfterCompletingExercises(MarathonerDto marathoner, List<CompletedExerciseData> completedExerciseDataList) {
        long updatedExperience = 0;
        for (CompletedExerciseData exerciseAndQuantity : completedExerciseDataList) {
            Exercise exercise = exerciseService.getByName(exerciseAndQuantity.getExerciseName());
            updatedExperience += exercise.getExperience() * exerciseAndQuantity.getCompletedQuantity();
        }
        marathoner.setExperience(marathoner.getExperience() + updatedExperience);
    }

    private void updateExperienceAfterCompletingTasks(MarathonerDto marathoner, DataForUpdateMarathonerAfterCompletingTheExercises data) {
        List<TaskDto> taskList = updateTaskListForCompletedExercisesAndStatus(marathoner, data.getCompletedExercises());
        for (TaskDto task : taskList) {
            marathoner.setExperience(marathoner.getExperience() + task.getExperience());
        }
        marathonersService.updateMarathoner(new UpdateMarathonerDto(
                new ObjectId(marathoner.getId()), marathoner.getName(), marathoner.getLevel(), marathoner.getExperience(), marathoner.getTasks()));
    }

    private List<TaskDto> updateTaskListForCompletedExercisesAndStatus(MarathonerDto marathoner, List<CompletedExerciseData> completedExerciseData) {
        completedExerciseData.forEach(exerciseData -> updateQuantityInTaskList(marathoner.getId(), exerciseData));
        List<TaskDto> notCompletedTasks = tasksService.getTasksByMarathonerIdAndTaskStatus(new ObjectId(marathoner.getId()), IN_PROGRESS);
        return notCompletedTasks.stream()
                .map(this::updateTaskForExercisesAndGetCompletedAfterLastUpdates)
                .collect(Collectors.toList());
    }

    private void updateQuantityInTaskList(String marathonerId, CompletedExerciseData exerciseData) {
        List<TaskDto> taskList = tasksService.getTasksByMarathonerIdAndExerciseName(new ObjectId(marathonerId), exerciseData.getExerciseName());
        taskList.forEach(task -> updateQuantityInTask(task, exerciseData));
    }

    private void updateQuantityInTask(TaskDto task, CompletedExerciseData exerciseData) {
        for (CompletedExerciseData exerciseDataFromList : task.getMarathonerExerciseDataWithGoalList()) {
            if (exerciseDataFromList.getExerciseName().equals(exerciseData.getExerciseName())) {
                task.setTaskStatus(IN_PROGRESS);
                exerciseDataFromList.setCompletedQuantity(exerciseDataFromList.getCompletedQuantity() + exerciseData.getCompletedQuantity());
                break;
            }
        }
        tasksService.updateTask(task);
    }

    private TaskDto updateTaskForExercisesAndGetCompletedAfterLastUpdates(TaskDto task) {
        boolean isTaskDone = false;
        for (CompletedExerciseDataWithGoal exerciseData : task.getMarathonerExerciseDataWithGoalList()) {
            if (exerciseData.getCompletedQuantity() >= exerciseData.getGoalQuantity()) {
                isTaskDone = true;
            } else {
                isTaskDone = false;
                break;
            }
        }
        if (isTaskDone) {
            task.setTaskStatus(DONE);
            tasksService.updateTask(task);
        }
        return task;
    }

    private void updateMarathonerLevelIfNeeded(MarathonerDto marathoner) {
        while (marathonerExperienceBiggerThenLevelExperienceValue(marathoner)) {
            int newLevel = marathoner.getLevel() + 1;
            long updatedExperience = marathoner.getExperience() - levelService.getLevelByNumber(newLevel).getExperienceValue();
            marathoner.setLevel(newLevel);
            marathoner.setExperience(updatedExperience);
            log.info("Marathoner {} up level to {}", marathoner.getId(), newLevel);
            marathonersService.updateMarathoner(new UpdateMarathonerDto(
                    new ObjectId(marathoner.getId()), marathoner.getName(), marathoner.getLevel(), marathoner.getExperience(), marathoner.getTasks()));
        }
    }

    private boolean marathonerExperienceBiggerThenLevelExperienceValue(MarathonerDto marathoner) {
        Level level = levelService.getLevelByNumber(marathoner.getLevel());
        return marathoner.getExperience() >= level.getExperienceValue();
    }

    private void updateTotalQuantityOfExercise(DataForUpdateMarathonerAfterCompletingTheExercises completedExercisesData) {
        List<ExerciseWithTotalQuantity> exercisesForSaving = new ArrayList<>();
        List<CompletedExerciseData> notExecutedExercises = new ArrayList<>(completedExercisesData.getCompletedExercises());
        Map<String, ExerciseWithTotalQuantity> marathonerExercises = totalQuantityOfExerciseService.getAllByMarathonerId(completedExercisesData.getMarathonerId())
                .stream()
                .parallel()
                .collect(Collectors.toMap(ExerciseWithTotalQuantity::getExercise, exercise -> exercise));
        for (CompletedExerciseData completedExercise : completedExercisesData.getCompletedExercises()) {
            notExecutedExercises.remove(completedExercise);
            ExerciseWithTotalQuantity executedExerciseRecord = marathonerExercises.get(completedExercise.getExerciseName());
            executedExerciseRecord.setQuantity(executedExerciseRecord.getQuantity() + completedExercise.getCompletedQuantity());
            executedExerciseRecord.setLastUpdate(LocalDateTime.now());
            exercisesForSaving.add(executedExerciseRecord);
        }

        for (CompletedExerciseData completedExercise : notExecutedExercises) {
            exercisesForSaving.add(ExerciseWithTotalQuantity.builder()
                    .marathonerId(completedExercisesData.getMarathonerId().toHexString())
                    .exercise(completedExercise.getExerciseName())
                    .quantity(completedExercise.getCompletedQuantity())
                    .lastUpdate(LocalDateTime.now())
                    .build());
        }
        totalQuantityOfExerciseService.saveTotalQuantityOfExerciseList(exercisesForSaving);
    }

}
