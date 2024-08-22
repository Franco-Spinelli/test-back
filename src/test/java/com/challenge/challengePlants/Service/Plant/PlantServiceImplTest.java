package com.challenge.challengePlants.Service.Plant;

import com.challenge.challengePlants.DTO.PlantDTO;
import com.challenge.challengePlants.Model.Plant;
import com.challenge.challengePlants.Model.SensorSummary;
import com.challenge.challengePlants.Model.User.Role;
import com.challenge.challengePlants.Model.User.User;
import com.challenge.challengePlants.Repository.PlantRepository;
import com.challenge.challengePlants.Service.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlantServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private PlantRepository plantRepository;
    @InjectMocks
    private PlantServiceImpl underTest;
    private Plant plant;
    private  User user;
    private PlantDTO plantDTO;
    @BeforeEach
    void setUp() {
        user = User.builder()
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
        plantDTO = PlantDTO.builder()
                .id(1L)
                .name("Mendoza")
                .country("Argentina")
                .build();
    }

    @Test
    void savePlant() {
        //GIVEN
        given(plantRepository.save(any(Plant.class))).willReturn(plant);
        //WHEN
        Plant newPlant = underTest.savePlant(plant);
        //THEN
        assertThat(newPlant).isNotNull();
    }

    @Test
    void createPlant() {
        // GIVEN
        Plant newPlantWithOutId = Plant.builder()
                .user(user)
                .id(null)
                .name("Mendoza")
                .country("Argentina")
                .build();
        given(userService.getUserAuthenticated()).willReturn(Optional.of(user));
        given(plantRepository.save(newPlantWithOutId)).willReturn(plant);
        // WHEN
        PlantDTO result = underTest.createPlant(plantDTO);
        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(plantDTO.getName());
        assertThat(result.getCountry()).isEqualTo(plantDTO.getCountry());
    }

    @Test
    void updatePlant() {
        // GIVEN
        given(plantRepository.findById(plant.getId())).willReturn(Optional.of(plant));
        given(plantRepository.save(plant)).willReturn(plant);
        // WHEN
        PlantDTO result = underTest.updatePlant(plantDTO);
        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(plantDTO.getName());
        assertThat(result.getCountry()).isEqualTo(plantDTO.getCountry());
    }
    @Test
    void updatePlant_shouldThrowException() {
        // GIVEN
        given(plantRepository.findById(plantDTO.getId())).willReturn(Optional.empty());
        // WHEN & THEN
        assertThrows(EntityNotFoundException.class, () -> underTest.updatePlant(plantDTO));
        verify(plantRepository, never()).save(any(Plant.class));
    }
    @Test
    void deletePlant() {
        // GIVEN
        Long id = 1L;
        given(plantRepository.findById(id)).willReturn(Optional.of(plant));
        // WHEN
        underTest.deletePlant(id);
        // THEN
        then(plantRepository).should().deleteById(id);
    }
    @Test
    void deletePlant_whenDoesNotExist() {
        // GIVEN
        Long id = 1L;
        given(plantRepository.findById(id)).willReturn(Optional.empty());
        // WHEN & THEN
        assertThrows(EntityNotFoundException.class, () -> {
            underTest.deletePlant(id);
        });
        then(plantRepository).should(never()).deleteById(anyLong());
    }
    @Test
    void findById() {
        // GIVEN
        Long id = 1L;
        given(plantRepository.findById(id)).willReturn(Optional.of(plant));
        // WHEN
        Optional<Plant> result = underTest.findById(id);
        // THEN
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(plant);
    }
}