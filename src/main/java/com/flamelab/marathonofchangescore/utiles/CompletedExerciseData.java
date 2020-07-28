package com.flamelab.marathonofchangescore.utiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompletedExerciseData {
    private String exerciseName;
    private int completedQuantity;
}
