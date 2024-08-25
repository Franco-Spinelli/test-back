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
    @GetMapping("/get-readings")
    public ResponseEntity<Integer> getReadings(){
        return ResponseEntity.ok(sensorSummaryService.getOkReadings());
    }
    @GetMapping("/get-medium")
    public ResponseEntity<Integer> getMedium(){
        return ResponseEntity.ok(sensorSummaryService.getMediumAlerts());
    }
    @GetMapping("/get-red")
    public ResponseEntity<Integer> getRead(){
        return ResponseEntity.ok(sensorSummaryService.getRedAlerts());
    }
    @GetMapping("/get-disabled")
    public ResponseEntity<Integer> getDisabled(){
        return ResponseEntity.ok(sensorSummaryService.getDisabledSensors());
    }
    @PostMapping("/add-sensor")
    public ResponseEntity<?> addSensor(@RequestBody SensorSummaryDTO sensorSummaryDTO){
        return ResponseEntity.ok( sensorSummaryService.createSensorSummary(sensorSummaryDTO));
    }
    @PutMapping("/update-sensor")
    public ResponseEntity<?> updateSensor(@RequestBody SensorSummaryDTO sensorSummaryDTO){
        return ResponseEntity.ok(sensorSummaryService.updateSensorSummary(sensorSummaryDTO));
    }
}
