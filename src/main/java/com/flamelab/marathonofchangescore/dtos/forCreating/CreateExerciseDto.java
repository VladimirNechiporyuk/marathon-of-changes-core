package com.flamelab.marathonofchangescore.dtos.forCreating;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateExerciseDto {
    private String name;
    private long experience;
    private String iterationType;
}
