package org.example.usermicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class DeviceClientService {

    @Autowired
    private RestTemplate restTemplate;

    public void deleteUserDevices(Long userId) {
        // call the delete method in DeviceController
        String DEVICE_API_URL = "http://device-microservice:8081/api/devices";
        String url = DEVICE_API_URL + "/userDevice/" + userId;
        restTemplate.delete(url);
    }
}
