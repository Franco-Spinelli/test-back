package com.challenge.challengePlants.Service.SensorSummary;

import com.challenge.challengePlants.DTO.SensorSummaryDTO;
import com.challenge.challengePlants.Exception.SensorCountMismatchException;
import com.challenge.challengePlants.Model.Enums.SensorType;
import com.challenge.challengePlants.Model.Plant;
import com.challenge.challengePlants.Model.SensorSummary;
import com.challenge.challengePlants.Model.User.Role;
import com.challenge.challengePlants.Model.User.User;
import com.challenge.challengePlants.Repository.SensorSummaryRepository;
import com.challenge.challengePlants.Service.Plant.PlantService;
import com.challenge.challengePlants.Service.Plant.PlantServiceImpl;
import com.challenge.challengePlants.Service.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class SensorSummaryServiceTest {
    @Mock
    private PlantService plantService;
    @Mock
    private SensorSummaryRepository sensorSummaryRepository;
    @InjectMocks
    private SensorSummaryServiceImpl underTest;
    private Plant plant;
    private SensorSummaryDTO sensorDTO;
    private SensorSummary sensorSummary;
    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1)
                .username("exampleUser")
                .password("password")
                .firstname("Franco")
                .lastname("Spinelli")
                .role(Role.USER)
                .build();
        plant = Plant.builder()
                .user(user)
                .id(1L)
                .name("Mendoza")
                .country("Argentina")
                .build();
        sensorDTO = SensorSummaryDTO.builder()
                .id(1L)
                .plant_id(plant.getId())
                .totalSensors(3)
                .disabledSensors(0)
                .redAlerts(2)
                .mediumAlerts(0)
                .okReadings(1)
                .type(SensorType.ENERGY)
                .build();
        sensorSummary = SensorSummary.builder()
                .id(1L)
                .disabledSensors(0)
                .totalSensors(0)
                .mediumAlerts(0)
                .okReadings(0)
                .redAlerts(0)
                .type(SensorType.ENERGY)
                .plant(plant)
                .build();
    }

    @Test
    void saveSensorSummary() {
        //GIVEN
        given(sensorSummaryRepository.save(any(SensorSummary.class))).willReturn(sensorSummary);
        //WHEN
        SensorSummary sensor = underTest.saveSensorSummary(sensorSummary);
        //THEN
        assertThat(sensor).isNotNull();
    }

    @Test
    void createSensorSummary() {
        //GIVEN
        given(plantService.findById(plant.getId())).willReturn(Optional.ofNullable(plant));
        given(underTest.saveSensorSummary(any(SensorSummary.class))).willReturn(sensorSummary);
        //WHEN
        SensorSummaryDTO sensorSummaryDTO = underTest.createSensorSummary(sensorDTO);
        //THEN
        assertThat(sensorSummaryDTO).isNotNull();
        assertThat(underTest.checkSensorCount(sensorDTO)).isTrue();
    }
    @Test
    void createSensorSummary_whenSensorCountMismatch(){
        //GIVEN
        SensorSummaryDTO invalidSensor = SensorSummaryDTO.builder()
                .id(1L)
                .plant_id(plant.getId())
                .totalSensors(1)
                .disabledSensors(0)
                .redAlerts(0)
                .mediumAlerts(0)
                .okReadings(0)
                .type(SensorType.ENERGY)
                .build();
        given(plantService.findById(plant.getId())).willReturn(Optional.ofNullable(plant));
        // WHEN & THEN
        assertThrows(SensorCountMismatchException.class, () -> {
            underTest.createSensorSummary(invalidSensor);
        });

    }
    @Test
    void createSensorSummary_whenPlantNotFound(){
        //GIVEN
        given(plantService.findById(plant.getId())).willReturn(Optional.empty());
        // WHEN & THEN
        assertThrows(EntityNotFoundException.class, () -> {
            underTest.createSensorSummary(sensorDTO);
        });

    }

    @Test
    void updateSensorSummary() {
        //GIVEN
        given(sensorSummaryRepository.findById(sensorDTO.getId())).willReturn(Optional.of(sensorSummary));
        given(sensorSummaryRepository.save(any(SensorSummary.class))).willReturn(sensorSummary);
        //WHEN
        SensorSummaryDTO updatedSensor = underTest.updateSensorSummary(sensorDTO);
        //THEN
        assertThat(updatedSensor).isNotNull();
        assertThat(updatedSensor.getOkReadings()).isEqualTo(1);
        assertThat(updatedSensor.getRedAlerts()).isEqualTo(2);
    }
    @Test
    void updateSensorSummary_whenSensorCountMismatch() {
        //GIVEN
        SensorSummaryDTO sensorSummaryDTO = SensorSummaryDTO.builder()
                .id(1L)
                .plant_id(plant.getId())
                .totalSensors(4)
                .disabledSensors(0)
                .redAlerts(2)
                .mediumAlerts(0)
                .okReadings(1)
                .type(SensorType.ENERGY)
                .build();
        given(sensorSummaryRepository.findById(sensorSummaryDTO.getId())).willReturn(Optional.of(sensorSummary));
        // WHEN & THEN
        assertThrows(SensorCountMismatchException.class, () -> {
            underTest.updateSensorSummary(sensorSummaryDTO);
        });
    }

    @Test
    void deleteSensorSummary() {
        // GIVEN
        Long id = 1L;
        given(sensorSummaryRepository.findById(id)).willReturn(Optional.of(sensorSummary));
        // WHEN
        underTest.deleteSensorSummary(id);
        // THEN
        then(sensorSummaryRepository).should().deleteById(id);
    }
    @Test
    void deleteSensorSummary_whenDoesNotExist() {
        // GIVEN
        Long id = 1L;
        given(sensorSummaryRepository.findById(id)).willReturn(Optional.empty());
        // WHEN & THEN
        assertThrows(EntityNotFoundException.class, () -> {
            underTest.deleteSensorSummary(id);
        });
        then(sensorSummaryRepository).should(never()).deleteById(anyLong());
    }

    @Test
    void findById() {
        // GIVEN
        Long id = 1L;
        given(sensorSummaryRepository.findById(id)).willReturn(Optional.of(sensorSummary));
        // WHEN
        Optional<SensorSummary> result = underTest.findById(id);
        // THEN
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(sensorSummary);
        then(sensorSummaryRepository).should().findById(id);
    }

}