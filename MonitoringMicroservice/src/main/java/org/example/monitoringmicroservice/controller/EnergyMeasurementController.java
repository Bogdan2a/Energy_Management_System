package org.example.monitoringmicroservice.controller;

import org.example.monitoringmicroservice.entity.EnergyMeasurement;
import org.example.monitoringmicroservice.service.EnergyMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitoring")
public class EnergyMeasurementController {

    @Autowired
    private EnergyMeasurementService energyMeasurementService;

    @GetMapping
    public List<EnergyMeasurement> getAllEnergyMeasurements() {
        return energyMeasurementService.getAllEnergyMeasurements();
    }
}
