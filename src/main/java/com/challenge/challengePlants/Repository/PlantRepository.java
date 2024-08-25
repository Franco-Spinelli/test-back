package com.challenge.challengePlants.Repository;

import com.challenge.challengePlants.Model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant,Long> {
    @Query("SELECT p FROM Plant p WHERE p.user.id = :userId")
    List<Plant> findPlantsByUserId(@Param("userId") Integer userId);
}
