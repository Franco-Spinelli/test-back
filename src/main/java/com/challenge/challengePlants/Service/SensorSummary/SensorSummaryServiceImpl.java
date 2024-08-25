package com.challenge.challengePlants.Service.SensorSummary;

import com.challenge.challengePlants.DTO.SensorSummaryDTO;
import com.challenge.challengePlants.Exception.SensorCountMismatchException;
import com.challenge.challengePlants.Exception.SensorTypeAlreadyExistsException;
import com.challenge.challengePlants.Model.Plant;
import com.challenge.challengePlants.Model.SensorSummary;
import com.challenge.challengePlants.Repository.SensorSummaryRepository;
import com.challenge.challengePlants.Service.Plant.PlantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SensorSummaryServiceImpl implements SensorSummaryService {
    private SensorSummaryRepository sensorSummaryRepository;
    private PlantService plantService;
    @Override
    public SensorSummary saveSensorSummary(SensorSummary sensorSummary) {
        return sensorSummaryRepository.save(sensorSummary);
    }

    @Override
    public SensorSummaryDTO createSensorSummary(SensorSummaryDTO sensorSummaryDTO) {
        Optional<Plant> optionalPlant = plantService.findById(sensorSummaryDTO.getPlant_id());
        if(optionalPlant.isPresent()){
            if(checkSensorCount(sensorSummaryDTO)){
                Plant plant = optionalPlant.get();
                if(checkSensorType(sensorSummaryDTO,plant)){
                    SensorSummary newSensor = saveSensorSummary(SensorSummary.builder()
                            .disabledSensors(sensorSummaryDTO.getDisabledSensors())
                            .totalSensors(sensorSummaryDTO.getTotalSensors())
                            .type(sensorSummaryDTO.getType())
                            .plant(plant)
                            .mediumAlerts(sensorSummaryDTO.getMediumAlerts())
                            .okReadings(sensorSummaryDTO.getOkReadings())
                            .redAlerts(sensorSummaryDTO.getRedAlerts())
                            .build());
                    return sensorSummaryToSensorSummaryDTO(newSensor);
                }else {
                    throw new SensorTypeAlreadyExistsException("Sensor " + sensorSummaryDTO.getType().toString() + " type already exists. Please update the existing list.");
                }

            }else {
                throw new SensorCountMismatchException("Sensor count mismatch: Total sensors do not match the sum of disabled, alert, and reading sensors.");
            }
        } else {
            throw new EntityNotFoundException("Plant not found");
        }
    }

    @Override
    public SensorSummaryDTO updateSensorSummary(SensorSummaryDTO sensorSummaryDTO) {
        SensorSummary currentSensor = sensorSummaryRepository.findById(sensorSummaryDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));
        if(checkUpdateSensor(sensorSummaryDTO,currentSensor)){

            if (sensorSummaryDTO.getId() != null) {
                currentSensor.setId(sensorSummaryDTO.getId());
            }
            if (sensorSummaryDTO.getType() != null) {
                currentSensor.setType(sensorSummaryDTO.getType());
            }
            if (sensorSummaryDTO.getTotalSensors() != null) {
                currentSensor.setTotalSensors(sensorSummaryDTO.getTotalSensors());
            }
            if (sensorSummaryDTO.getOkReadings() != null) {
                currentSensor.setOkReadings(sensorSummaryDTO.getOkReadings());
            }
            if (sensorSummaryDTO.getMediumAlerts() != null) {
                currentSensor.setMediumAlerts(sensorSummaryDTO.getMediumAlerts());
            }
            if (sensorSummaryDTO.getRedAlerts() != null) {
                currentSensor.setRedAlerts(sensorSummaryDTO.getRedAlerts());
            }
            if (sensorSummaryDTO.getDisabledSensors() != null) {
                currentSensor.setDisabledSensors(sensorSummaryDTO.getDisabledSensors());
            }
            SensorSummary newSensor = saveSensorSummary(currentSensor);
            return sensorSummaryToSensorSummaryDTO(newSensor);
        }else {
            throw new SensorCountMismatchException("Sensor count mismatch: Total sensors do not match the sum of disabled, alert, and reading sensors.");
        }
    }

    @Override
    public void deleteSensorSummary(Long id) {
        Optional<SensorSummary>optionalSensorSummary= findById(id);
        if (optionalSensorSummary.isEmpty()) {
            throw new EntityNotFoundException("The Sensor Summary doesn't exist");
        }
        sensorSummaryRepository.deleteById(id);
    }

    @Override
    public Optional<SensorSummary> findById(Long id) {
        return sensorSummaryRepository.findById(id);
    }

    @Override
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


    @Override
    public Integer getOkReadings() {
        List<Plant> plantList = plantService.getPlants();
        int alertsCount = 0;
        for (Plant plant:plantList) {
            for (SensorSummary sensorSummary:plant.getSensorSummaries()) {
                alertsCount = alertsCount + sensorSummary.getOkReadings();
            }
        }
        return alertsCount;
    }

    @Override
    public Integer getMediumAlerts() {
        List<Plant> plantList = plantService.getPlants();
        int readingsCount = 0;
        for (Plant plant:plantList) {
            for (SensorSummary sensorSummary:plant.getSensorSummaries()) {
                readingsCount = readingsCount + sensorSummary.getMediumAlerts();
            }
        }
        return readingsCount;
    }

    @Override
    public Integer getRedAlerts() {
        List<Plant> plantList = plantService.getPlants();
        int alertsCount = 0;
        for (Plant plant:plantList) {
            for (SensorSummary sensorSummary:plant.getSensorSummaries()) {
                alertsCount = alertsCount + sensorSummary.getRedAlerts();
            }
        }
        return alertsCount;
    }

    @Override
    public Integer getDisabledSensors() {
        List<Plant> plantList = plantService.getPlants();
        int sensorsCount = 0;
        for (Plant plant:plantList) {
            for (SensorSummary sensorSummary:plant.getSensorSummaries()) {
                sensorsCount = sensorsCount + sensorSummary.getDisabledSensors();
            }
        }
        return sensorsCount;
    }

    public boolean checkSensorType(SensorSummaryDTO sensorSummaryDTO, Plant plant) {
            for (SensorSummary sensorSummary:plant.getSensorSummaries()) {
               if(sensorSummary.getType().equals(sensorSummaryDTO.getType())){
                   return false;
               }
            }
        return true;
    }

    public boolean checkSensorCount(SensorSummaryDTO sensorSummaryDTO){
        return sensorSummaryDTO.getTotalSensors() == sensorSummaryDTO.getDisabledSensors() + sensorSummaryDTO.getRedAlerts() + sensorSummaryDTO.getMediumAlerts() + sensorSummaryDTO.getOkReadings();
    }
    public boolean checkUpdateSensor(SensorSummaryDTO sensorSummaryDTO, SensorSummary currentSensor){
        SensorSummary testNewSensor = currentSensor;
        if (sensorSummaryDTO.getId() != null) {
            testNewSensor.setId(sensorSummaryDTO.getId());
        }
        if (sensorSummaryDTO.getType() != null) {
            testNewSensor.setType(sensorSummaryDTO.getType());
        }
        if (sensorSummaryDTO.getTotalSensors() != null) {
            testNewSensor.setTotalSensors(sensorSummaryDTO.getTotalSensors());
        }
        if (sensorSummaryDTO.getOkReadings() != null) {
            testNewSensor.setOkReadings(sensorSummaryDTO.getOkReadings());
        }
        if (sensorSummaryDTO.getMediumAlerts() != null) {
            testNewSensor.setMediumAlerts(sensorSummaryDTO.getMediumAlerts());
        }
        if (sensorSummaryDTO.getRedAlerts() != null) {
            testNewSensor.setRedAlerts(sensorSummaryDTO.getRedAlerts());
        }
        if (sensorSummaryDTO.getDisabledSensors() != null) {
            testNewSensor.setDisabledSensors(sensorSummaryDTO.getDisabledSensors());
        }

        if (checkSensorCount( sensorSummaryToSensorSummaryDTO(testNewSensor))) {
            return true;
        }else {
            return false;
        }
    }
}
