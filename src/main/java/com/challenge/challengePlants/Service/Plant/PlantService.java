package com.challenge.challengePlants.Service.Plant;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.Model.Plant;
import org.springframework.stereotype.Service;

public interface PlantService {
    Plant savePlant(Plant plant);
    Plant createPlant(PlantDTO plantDTO);
    Plant updatePlant(PlantDTO plantDTO);
    void deletePlant(Long id);
}
