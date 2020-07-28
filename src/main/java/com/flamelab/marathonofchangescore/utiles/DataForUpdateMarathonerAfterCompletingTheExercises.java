package com.flamelab.marathonofchangescore.utiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForUpdateMarathonerAfterCompletingTheExercises {
    private ObjectId marathonerId;
    private List<CompletedExerciseData> completedExercises;
}
