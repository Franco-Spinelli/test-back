package com.challenge.challengePlants.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantDTO {
    private Long id;
    private String name;
    private String country;
    private List<SensorSummaryDTO>sensorSummaryDTOList;
}
