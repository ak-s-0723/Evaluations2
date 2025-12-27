package org.example.evaluations2.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "my-topic", groupId = "service1")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}