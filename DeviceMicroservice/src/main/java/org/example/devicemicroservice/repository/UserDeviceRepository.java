package org.example.devicemicroservice.repository;

import org.example.devicemicroservice.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_devices (user_id, device_id) VALUES (:userId, :deviceId)", nativeQuery = true)
    void linkUserToDevice(Long userId, Long deviceId);

    UserDevice findByDeviceId(Long deviceId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_devices WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(Long userId);
}
