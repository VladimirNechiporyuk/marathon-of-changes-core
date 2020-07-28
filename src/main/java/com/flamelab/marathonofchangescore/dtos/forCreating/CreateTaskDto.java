package com.flamelab.marathonofchangescore.dtos.forCreating;

import com.flamelab.marathonofchangescore.utiles.CompletedExerciseDataWithGoal;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateTaskDto {
    private String taskName;
    private long taskExperience;
    private ObjectId marathonerId;
    private String taskType;
    private List<CompletedExerciseDataWithGoal> marathonerExerciseDataWithGoalList;
}
