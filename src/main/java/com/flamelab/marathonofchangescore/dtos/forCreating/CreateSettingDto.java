package com.flamelab.marathonofchangescore.dtos.forCreating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSettingDto {
    private String name;
    private String value;
}
