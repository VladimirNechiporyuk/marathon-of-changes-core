package com.flamelab.marathonofchangescore.dtos.forUpdating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExerciseDto {
    private ObjectId exerciseId;
    private String name;
    private long experience;
    private String iterationType;
}
