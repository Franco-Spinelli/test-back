package com.challenge.challengePlants.Controller;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.DTO.SensorSummaryDTO;
import com.challenge.challengePlants.Service.Plant.PlantService;
import com.challenge.challengePlants.Service.SensorSummary.SensorSummaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    private SensorSummaryService sensorSummaryService;
    @PostMapping("/add-sensor")
    public ResponseEntity<?> addPlant(@RequestBody SensorSummaryDTO sensorSummaryDTO){
        return ResponseEntity.ok( sensorSummaryService.createSensorSummary(sensorSummaryDTO));
    }
    @PutMapping("/update-sensor")
    public ResponseEntity<?> updatePlant(@RequestBody SensorSummaryDTO sensorSummaryDTO){
        return ResponseEntity.ok(sensorSummaryService.updateSensorSummary(sensorSummaryDTO));
    }
}
