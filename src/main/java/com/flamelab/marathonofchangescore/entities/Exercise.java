package com.flamelab.marathonofchangescore.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("exercises")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {

    @Id
    @JsonProperty("id")
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("experience")
    private long experience;

    @JsonProperty("iterationType")
    private String iterationType;
}
