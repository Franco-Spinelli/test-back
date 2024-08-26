package com.challenge.challengePlants.Service.Plant;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.DTO.SensorSummaryDTO;
import com.challenge.challengePlants.Model.Plant;
import com.challenge.challengePlants.Model.SensorSummary;
import com.challenge.challengePlants.Model.User.User;
import com.challenge.challengePlants.Repository.PlantRepository;
import com.challenge.challengePlants.Service.SensorSummary.SensorSummaryService;
import com.challenge.challengePlants.Service.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public PlantDTO createPlant(PlantDTO plantDTO) {
        User user = userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
       Plant newPlant = plantRepository.save(Plant.builder()
                        .country(plantDTO.getCountry())
                        .name(plantDTO.getName())
                        .sensorSummaries(new ArrayList<>())
                        .user(user)
                .build());
       return plantToPlantDTO(newPlant);
    }

    @Override
    public PlantDTO updatePlant(PlantDTO plantDTO) {
        Optional<Plant> optionalPlant = findById(plantDTO.getId());
        if(optionalPlant.isPresent()){
            Plant existingPlant = optionalPlant.get();
            if (plantDTO.getName() != null) {
                existingPlant.setName(plantDTO.getName());
            }
            if (plantDTO.getCountry() != null) {
                existingPlant.setCountry(plantDTO.getCountry());
            }
            return plantToPlantDTO(plantRepository.save(existingPlant));
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

    @Override
    public PlantDTO plantToPlantDTO(Plant plant) {
        return PlantDTO.builder()
                .id(plant.getId())
                .country(plant.getCountry())
                .name(plant.getName())
                .sensorSummaryDTOList(sensorSummaryListToSensorSummaryDTOList(plant.getSensorSummaries()))
                .build();
    }

    @Override
    public List<PlantDTO> getPlantsDTO() {
        User user = userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        List<Plant>plantList = plantRepository.findPlantsByUserId(user.getId());
        List<PlantDTO>plantDTOS = new ArrayList<>();
        for (Plant plant: plantList) {
            plantDTOS.add(plantToPlantDTO(plant));
        }
        return plantDTOS;
    }

    @Override
    public List<Plant> getPlants() {
        User user = userService.getUserAuthenticated().orElseThrow(() -> new IllegalStateException("Unauthenticated user"));
        return plantRepository.findPlantsByUserId(user.getId());
    }
    public List<SensorSummaryDTO> sensorSummaryListToSensorSummaryDTOList(List<SensorSummary> sensorSummaryList) {

        List<SensorSummaryDTO>sensorSummaryDTOList = new ArrayList<>();
        if(sensorSummaryList != null){
            for (SensorSummary sensorSummary:sensorSummaryList) {
                sensorSummaryDTOList.add(sensorSummaryToSensorSummaryDTO(sensorSummary));
            }
            return sensorSummaryDTOList;
        }

        return sensorSummaryDTOList;
    }
    public SensorSummaryDTO sensorSummaryToSensorSummaryDTO(SensorSummary sensorSummary) {

        return SensorSummaryDTO.builder()
                .id(sensorSummary.getId())
                .disabledSensors(sensorSummary.getDisabledSensors())
                .totalSensors(sensorSummary.getTotalSensors())
                .mediumAlerts(sensorSummary.getMediumAlerts())
                .redAlerts(sensorSummary.getRedAlerts())
                .okReadings(sensorSummary.getOkReadings())
                .plant_id(sensorSummary.getPlant().getId())
                .type(sensorSummary.getType())
                .build();
    }
}
