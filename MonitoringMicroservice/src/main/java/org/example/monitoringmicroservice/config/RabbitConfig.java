package org.example.monitoringmicroservice.config;

import com.rabbitmq.client.ConnectionFactory;
import org.example.monitoringmicroservice.listener.EnergyReadingListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    private static final String QUEUE_NAME = "energy_readings_queue"; // Queue from simulator
    private static final String RABBITMQ_URI = "amqps://ftbjhwux:8-N_fP7bHQnViz7F7Z_jmUUbXuj73j0A@kangaroo.rmq.cloudamqp.com/ftbjhwux";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri(RABBITMQ_URI);
        return connectionFactory;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(CachingConnectionFactory connectionFactory, EnergyReadingListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue());
        container.setMessageListener(listener);
        return container;
    }
}
