package org.example.evaluations2.services;

import org.example.evaluations2.dtos.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private static final String TOPIC = "order-events";

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void createOrder(OrderEvent order) {
        order.setStatus("CREATED");
        order.setOrderId(UUID.randomUUID().toString());

        kafkaTemplate.send(TOPIC, order.getOrderId(), order);
    }
}