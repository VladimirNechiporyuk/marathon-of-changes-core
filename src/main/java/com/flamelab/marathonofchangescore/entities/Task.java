package com.flamelab.marathonofchangescore.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flamelab.marathonofchangescore.dtos.TaskDto;
import com.flamelab.marathonofchangescore.enums.TaskStatus;
import com.flamelab.marathonofchangescore.enums.TaskType;
import com.flamelab.marathonofchangescore.utiles.CompletedExerciseDataWithGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @JsonProperty("_id")
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("experience")
    private long experience;

    @JsonProperty("executor")
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId marathonerId;

    @JsonProperty("status")
    private TaskStatus taskStatus;

    @JsonProperty("type")
    private TaskType taskType;

    @JsonProperty("createdDate")
    private LocalDateTime createdDate;

    @JsonProperty("exercisesList")
    private List<CompletedExerciseDataWithGoal> marathonerExerciseDataWithGoalList;

    public TaskDto toDto() {
        return TaskDto.builder()
                .id(this.id.toHexString())
                .name(this.name)
                .experience(this.experience)
                .marathonerId(this.marathonerId.toHexString())
                .taskStatus(this.taskStatus)
                .taskType(this.taskType)
                .createdDate(this.createdDate)
                .marathonerExerciseDataWithGoalList(this.marathonerExerciseDataWithGoalList)
                .build();
    }
}
