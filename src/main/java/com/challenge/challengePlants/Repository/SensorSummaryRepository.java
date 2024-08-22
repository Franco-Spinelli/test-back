package com.challenge.challengePlants.Repository;

import com.challenge.challengePlants.Model.SensorSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorSummaryRepository  extends JpaRepository<SensorSummary,Long> {
}
