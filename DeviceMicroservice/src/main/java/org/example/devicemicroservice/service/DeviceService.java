package org.example.devicemicroservice.service;

import org.example.devicemicroservice.entity.Device;
import org.example.devicemicroservice.entity.UserDevice;
import org.example.devicemicroservice.repository.DeviceRepository;
import org.example.devicemicroservice.repository.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Optional<Device> updateDevice(Long id, Device deviceDetails) {
        return deviceRepository.findById(id).map(device -> {
            device.setDescription(deviceDetails.getDescription());
            device.setAddress(deviceDetails.getAddress());
            device.setMaxHourlyConsumption(deviceDetails.getMaxHourlyConsumption());
            return deviceRepository.save(device);
        });
    }

    public boolean deleteDevice(Long id) {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public UserDevice linkDeviceToUser(Long userId, Long deviceId) {
        UserDevice userDevice = new UserDevice();
        userDevice.setUserId(userId);
        userDevice.setDeviceId(deviceId);
        return userDeviceRepository.save(userDevice);
    }

    public List<Device> getDevicesForUser(Long userId) {
        List<UserDevice> allUserDevices = userDeviceRepository.findAll();

        return allUserDevices.stream()
                .filter(userDevice -> userDevice.getUserId().equals(userId))
                .map(userDevice -> deviceRepository.findById(userDevice.getDeviceId()).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }
}
