package com.challenge.challengePlants.Service.SensorSummary;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.DTO.SensorSummaryDTO;
import com.challenge.challengePlants.Model.Plant;
import com.challenge.challengePlants.Model.SensorSummary;

import java.util.Optional;

public interface SensorSummaryService {
    SensorSummary saveSensorSummary(SensorSummary sensorSummary);
    SensorSummaryDTO createSensorSummary(SensorSummaryDTO sensorSummaryDTO);
    SensorSummaryDTO updateSensorSummary(SensorSummaryDTO sensorSummaryDTO);
    void deleteSensorSummary(Long id);
    Optional<SensorSummary> findById(Long id);
    SensorSummaryDTO sensorSummaryToSensorSummaryDTO(SensorSummary sensorSummary);
}
