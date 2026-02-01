package org.example.evaluations2.consumers;

import org.example.evaluations2.dtos.OrderEvent;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private final Integer MAX_ORDER_QUANTITY_LIMIT = 100;

    public void consume(OrderEvent order) {
        try {
            processOrder(order);
        } catch (Exception ex) {
            order.setStatus("FAILED");
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void processOrder(OrderEvent order) {
    }
}
