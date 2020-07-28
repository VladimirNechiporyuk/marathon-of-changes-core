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

import java.time.LocalDateTime;

@Data
@Document("exercise_with_total_quantity_for_marathoner")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseWithTotalQuantity {

    @Id
    @JsonProperty("_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @JsonProperty("marathonerId")
    @JsonSerialize(using = ToStringSerializer.class)
    private String marathonerId;

    @JsonProperty("exercise")
    private String exercise;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;
}
