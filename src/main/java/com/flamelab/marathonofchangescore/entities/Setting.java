package com.flamelab.marathonofchangescore.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document("settings")
@NoArgsConstructor
@AllArgsConstructor
public class Setting {

    @Id
    @JsonProperty("_id")
    private ObjectId id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

}
