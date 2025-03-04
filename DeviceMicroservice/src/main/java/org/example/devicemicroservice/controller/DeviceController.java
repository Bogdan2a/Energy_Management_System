package org.example.devicemicroservice.controller;

import org.example.devicemicroservice.entity.Device;
import org.example.devicemicroservice.service.DeviceService;
import org.example.devicemicroservice.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserDeviceService userDeviceService;

    @GetMapping("/isOn")
    public boolean isOn() {
        System.out.println("isOn");
        return true;
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        Device createdDevice = deviceService.createDevice(device);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Optional<Device> device = deviceService.getDeviceById(id);
        return device.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceDetails) {
        Optional<Device> updatedDevice = deviceService.updateDevice(id, deviceDetails);
        return updatedDevice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        if (deviceService.deleteDevice(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/link")
    public ResponseEntity<Void> linkUserToDevice(@RequestParam Long userId, @RequestParam Long deviceId) {
        System.out.println(userId + deviceId);
        userDeviceService.linkUserToDevice(userId, deviceId);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // return 201 Created status
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Device>> getDevicesForUser(@PathVariable Long userId) {
        System.out.println(userId);
        List<Device> devices = deviceService.getDevicesForUser(userId);
        System.out.println(devices);
        return ResponseEntity.ok(devices);
    }

    @DeleteMapping("/userDevice/{userId}")
    public ResponseEntity<Void> deleteUserDeviceByUserId(@PathVariable Long userId) {
        userDeviceService.deleteUserDeviceByUserId(userId);
        System.out.println("deleteUserDeviceByUserId");
        return ResponseEntity.noContent().build(); // return 204 No Content status
    }
}
