package com.flamelab.marathonofchangescore.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flamelab.marathonofchangescore.dtos.MarathonerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "marathoners")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marathoner {

    @Id
    @JsonProperty("_id")
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("level")
    private int level;

    @JsonProperty("experience")
    private long experience;

    @JsonProperty("tasks")
    private List<ObjectId> tasks;

    public MarathonerDto toDto() {
        return MarathonerDto.builder()
                .id(this.id.toHexString())
                .name(this.name)
                .level(this.level)
                .experience(this.experience)
                .tasks(this.tasks)
                .build();
    }

}
