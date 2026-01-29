package org.example.evaluations2.services;

import org.example.evaluations2.dtos.OrderEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateOrderAndSendKafkaMessage() {

        OrderEvent order = new OrderEvent();
        order.setQuantity(5);
        order.setPrice(1000);

        orderService.createOrder(order);


        assertEquals("CREATED", order.getStatus());


        assertNotNull(order.getOrderId());


        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<OrderEvent> orderCaptor = ArgumentCaptor.forClass(OrderEvent.class);

        verify(kafkaTemplate, times(1))
                .send(topicCaptor.capture(), keyCaptor.capture(), orderCaptor.capture());


        assertEquals("order-events", topicCaptor.getValue());


        assertEquals(order.getOrderId(), keyCaptor.getValue());


        assertEquals(order, orderCaptor.getValue());
    }
}
