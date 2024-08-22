package com.challenge.challengePlants.Repository;

import com.challenge.challengePlants.Model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant,Long> {
}
