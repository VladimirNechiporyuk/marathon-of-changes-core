package com.flamelab.marathonofchangescore.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("levels")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Level {

    @Id
    @JsonProperty("_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private int id;

    @JsonProperty("experienceValue")
    private long experienceValue;

}
