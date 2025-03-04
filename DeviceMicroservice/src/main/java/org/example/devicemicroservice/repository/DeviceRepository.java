package org.example.devicemicroservice.repository;

import org.example.devicemicroservice.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByDescription(String description);

}
