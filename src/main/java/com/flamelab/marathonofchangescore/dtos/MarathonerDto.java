package com.flamelab.marathonofchangescore.dtos;

import com.flamelab.marathonofchangescore.entities.Marathoner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarathonerDto {

    private String id;
    private String name;
    private int level;
    private long experience;
    private List<ObjectId> tasks;

    public Marathoner toEntity() {
        return Marathoner.builder()
                .id(new ObjectId(this.id))
                .name(this.name)
                .level(this.level)
                .experience(this.experience)
                .tasks(this.tasks)
                .build();
    }
}
