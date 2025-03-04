package org.example.monitoringmicroservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.monitoringmicroservice.entity.EnergyMeasurement;
import org.example.monitoringmicroservice.repository.EnergyMeasurementRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnergyReadingListener implements MessageListener {

    @Autowired
    private EnergyMeasurementRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message) {
        try {
            // parse the JSON message from the RabbitMQ queue
            String json = new String(message.getBody());
            System.out.println("Received message: " + json);
            EnergyMeasurement energyMeasurement = objectMapper.readValue(json, EnergyMeasurement.class);

            // save the energy measurement with timestamp as long
            repository.save(energyMeasurement);
            System.out.println("Saved energy reading: " + energyMeasurement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
