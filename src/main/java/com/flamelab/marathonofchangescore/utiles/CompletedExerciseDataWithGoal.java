package com.flamelab.marathonofchangescore.utiles;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class CompletedExerciseDataWithGoal extends CompletedExerciseData {

    private int goalQuantity;

    public CompletedExerciseDataWithGoal(String exerciseName, int completedQuantity, int goalQuantity) {
        super(exerciseName, completedQuantity);
        this.goalQuantity = goalQuantity;
    }
}
