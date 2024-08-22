package com.challenge.challengePlants.Service.Plant;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.Model.Plant;
import com.challenge.challengePlants.Repository.PlantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PlantServiceImpl implements PlantService {
    private PlantRepository plantRepository;
    @Override
    public Plant savePlant(Plant plant) {
        return plantRepository.save(plant);
    }

    @Override
    public Plant createPlant(PlantDTO plantDTO) {
        return null;
    }

    @Override
    public Plant updatePlant(PlantDTO plantDTO) {
        return null;
    }

    @Override
    public void deletePlant(Long id) {

    }
}
