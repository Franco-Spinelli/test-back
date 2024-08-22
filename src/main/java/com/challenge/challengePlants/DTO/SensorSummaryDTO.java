package com.challenge.challengePlants.DTO;

import com.challenge.challengePlants.Model.Enums.SensorType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorSummaryDTO {
    private Long id;
    private Long plant_id;
    private SensorType type;
    private Integer totalSensors;
    private Integer okReadings;
    private Integer mediumAlerts;
    private Integer redAlerts;
    private Integer disabledSensors;

}
