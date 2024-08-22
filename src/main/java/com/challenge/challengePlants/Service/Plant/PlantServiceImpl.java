package com.challenge.challengePlants.Service.Plant;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.Model.Plant;
import com.challenge.challengePlants.Model.User.User;
import com.challenge.challengePlants.Repository.PlantRepository;
import com.challenge.challengePlants.Service.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PlantServiceImpl implements PlantService {
    private PlantRepository plantRepository;
    private UserService userService;
    @Override
    public Plant savePlant(Plant plant) {
        return plantRepository.save(plant);
    }

    @Override
    public Plant createPlant(PlantDTO plantDTO) {
        User user = userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        return plantRepository.save(Plant.builder()
                        .country(plantDTO.getCountry())
                        .name(plantDTO.getName())
                        .user(user)
                .build());
    }

    @Override
    public Plant updatePlant(PlantDTO plantDTO) {
        Optional<Plant> optionalPlant = findById(plantDTO.getId());
        if(optionalPlant.isPresent()){
            Plant existingPlant = optionalPlant.get();
            if (plantDTO.getName() != null) {
                existingPlant.setName(plantDTO.getName());
            }
            if (plantDTO.getCountry() != null) {
                existingPlant.setCountry(plantDTO.getCountry());
            }
            return plantRepository.save(existingPlant);
        }else {
            throw new EntityNotFoundException("Plant not found");
        }
    }

    @Override
    public void deletePlant(Long id) {
        Optional<Plant>optionalPlant = findById(id);
        if (optionalPlant.isEmpty()) {
            throw new EntityNotFoundException("The plant doesn't exist");
        }
        plantRepository.deleteById(id);
    }

    @Override
    public Optional<Plant> findById(Long id) {
        return plantRepository.findById(id);
    }
}
