package com.challenge.challengePlants.Controller;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.Service.Plant.PlantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/plant")
public class PlantController {
    private PlantService plantService;
    @PostMapping("/add-plant")
    public ResponseEntity<?> addPlant(@RequestBody PlantDTO plantDTO){
        plantService.createPlant(plantDTO);
        return ResponseEntity.ok(plantDTO);
    }
    @PutMapping("/update-plant")
    public ResponseEntity<?> updatePlant(@RequestBody PlantDTO plantDTO){
        plantService.updatePlant(plantDTO);
        return ResponseEntity.ok(plantDTO);
    }
}
