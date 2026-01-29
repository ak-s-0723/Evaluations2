package org.example.evaluations2.consumers;


import org.example.evaluations2.dtos.OrderEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderConsumerTest {

    private OrderConsumer orderConsumer;

    @BeforeEach
    void setUp() {
        orderConsumer = new OrderConsumer();
    }

    @Test
    void shouldProcessOrderSuccessfully() {

        OrderEvent order = new OrderEvent();
        order.setOrderId("550e8400-e29b-4124-a716-443655440000");
        order.setQuantity(10);
        order.setPrice(500);

        orderConsumer.consume(order);

        assertEquals("PROCESSED", order.getStatus());
    }

    @Test
    void shouldFailWhenOrderIdIsNull() {

        OrderEvent order = new OrderEvent();
        order.setQuantity(10);
        order.setPrice(500);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderConsumer.consume(order)
        );

        assertEquals("FAILED", order.getStatus());
        assertTrue(exception.getMessage().contains("Received OrderId is null"));
    }

    @Test
    void shouldFailWhenQuantityExceedsLimit() {

        OrderEvent order = new OrderEvent();
        order.setOrderId("550e8400-e29b-41d4-a716-446655440000");
        order.setQuantity(101); // > MAX_ORDER_QUANTITY_LIMIT
        order.setPrice(500);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderConsumer.consume(order)
        );

        assertEquals("FAILED", order.getStatus());
        assertTrue(exception.getMessage().contains("Insufficient inventory"));
    }

    @Test
    void shouldFailWhenOrderIsNull() {

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderConsumer.consume(null)
        );

        assertNotNull(exception);
    }
}
