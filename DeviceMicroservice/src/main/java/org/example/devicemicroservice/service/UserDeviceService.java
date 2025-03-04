package org.example.devicemicroservice.service;

import org.example.devicemicroservice.entity.UserDevice;
import org.example.devicemicroservice.repository.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

@Service
public class UserDeviceService {

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    // link a user to a device
    public void linkUserToDevice(Long userId, Long deviceId) {
        // check if the link already exists
        UserDevice existingLink = userDeviceRepository.findByDeviceId(deviceId);
        if (existingLink != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Device already linked to this user.");
        }
        userDeviceRepository.linkUserToDevice(userId, deviceId);
    }
    public void deleteUserDeviceByUserId(Long userId) {
        // delete UserDevice entries based on userId
        userDeviceRepository.deleteByUserId(userId);
    }

}
