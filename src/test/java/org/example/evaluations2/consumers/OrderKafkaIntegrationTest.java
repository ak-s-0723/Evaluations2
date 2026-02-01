package org.example.evaluations2.consumers;

import org.example.evaluations2.dtos.OrderEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = { "order-events"},
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" }
)
@ActiveProfiles("test")
class OrderKafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @SpyBean
    private OrderConsumer orderConsumer;

    @Test
    void shouldConsumeAndProcessOrderSuccessfully() {

        OrderEvent order = new OrderEvent();
        order.setOrderId("550e9490-e39b-42d4-a716-446655440000");
        order.setQuantity(10);
        order.setPrice(500);

        kafkaTemplate.send("order-events", order.getOrderId(), order);

        await()
                .atMost(5, SECONDS)
                .untilAsserted(() ->
                        verify(orderConsumer, times(1))
                                .consume(any(OrderEvent.class))
                );
    }
}