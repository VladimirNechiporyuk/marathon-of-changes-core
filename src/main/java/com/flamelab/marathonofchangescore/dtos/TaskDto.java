package com.flamelab.marathonofchangescore.dtos;

import com.flamelab.marathonofchangescore.entities.Task;
import com.flamelab.marathonofchangescore.enums.TaskStatus;
import com.flamelab.marathonofchangescore.enums.TaskType;
import com.flamelab.marathonofchangescore.utiles.CompletedExerciseDataWithGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {

    private String id;
    private String name;
    private long experience;
    private String marathonerId;
    private TaskStatus taskStatus;
    private TaskType taskType;
    private LocalDateTime createdDate;
    private List<CompletedExerciseDataWithGoal> marathonerExerciseDataWithGoalList;

    public Task toEntity() {
        return Task.builder()
                .id(new ObjectId(this.id))
                .name(this.name)
                .experience(this.experience)
                .marathonerId(new ObjectId(this.id))
                .taskStatus(this.taskStatus)
                .taskType(this.taskType)
                .createdDate(this.createdDate)
                .marathonerExerciseDataWithGoalList(this.marathonerExerciseDataWithGoalList)
                .build();
    }

}
