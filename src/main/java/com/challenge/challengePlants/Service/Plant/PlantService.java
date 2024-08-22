package com.challenge.challengePlants.Service.Plant;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.Model.Plant;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface PlantService {
    Plant savePlant(Plant plant);
    PlantDTO createPlant(PlantDTO plantDTO);
    PlantDTO updatePlant(PlantDTO plantDTO);
    void deletePlant(Long id);
    Optional<Plant> findById(Long id);
    PlantDTO plantToPlantDTO(Plant plant);
}
