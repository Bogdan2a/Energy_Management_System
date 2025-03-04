package org.example.monitoringmicroservice.repository;

import org.example.monitoringmicroservice.entity.EnergyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyMeasurementRepository extends JpaRepository<EnergyMeasurement, Long> {
}
