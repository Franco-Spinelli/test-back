package com.challenge.challengePlants.Controller;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.Service.Plant.PlantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/plant")
public class PlantController {
    private PlantService plantService;
    @GetMapping("/get-plants")
    public ResponseEntity<?> getPlants(){
        return ResponseEntity.ok(plantService.getPlantsDTO());
    }
    @PostMapping("/add-plant")
    public ResponseEntity<?> addPlant(@RequestBody PlantDTO plantDTO){
        return ResponseEntity.ok( plantService.createPlant(plantDTO));
    }
    @PutMapping("/update-plant")
    public ResponseEntity<?> updatePlant(@RequestBody PlantDTO plantDTO){
        return ResponseEntity.ok(plantService.updatePlant(plantDTO));
    }
    @DeleteMapping("/delete-plant/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Delete success");
        return ResponseEntity.ok(response);
    }
}
