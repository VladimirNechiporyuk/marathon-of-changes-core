package com.flamelab.marathonofchangescore.dtos.forUpdating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMarathonerDto {

    private ObjectId id;
    private String name;
    private int level;
    private long experience;
    private List<ObjectId> tasks;
}
