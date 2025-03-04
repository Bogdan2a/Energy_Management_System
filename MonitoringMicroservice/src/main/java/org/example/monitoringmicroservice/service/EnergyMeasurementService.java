package org.example.monitoringmicroservice.service;

import org.example.monitoringmicroservice.entity.EnergyMeasurement;
import org.example.monitoringmicroservice.repository.EnergyMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyMeasurementService {

    @Autowired
    private EnergyMeasurementRepository energyMeasurementRepository;

    // Fetch all energy measurements from the database
    public List<EnergyMeasurement> getAllEnergyMeasurements() {
        return energyMeasurementRepository.findAll();
    }

}
