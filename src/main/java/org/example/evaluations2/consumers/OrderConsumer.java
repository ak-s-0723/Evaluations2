package org.example.evaluations2.consumers;

import org.example.evaluations2.dtos.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private final Integer MAX_ORDER_QUANTITY_LIMIT = 100;

    @KafkaListener(
            topics = "order-events",
            groupId = "order-group"
    )
    public void consume(OrderEvent order) {
        try {
            processOrder(order);
        } catch (Exception ex) {
            order.setStatus("FAILED");
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void processOrder(OrderEvent order) {
        String orderId = order.getOrderId();

        if(orderId == null) {
            order.setStatus("FAILED");
            throw new IllegalArgumentException("Received OrderId is null");
        }
        order.setStatus("PROCESSED");

        if (order.getQuantity() > MAX_ORDER_QUANTITY_LIMIT) {
            throw new RuntimeException("Insufficient inventory");
        }
    }
}
