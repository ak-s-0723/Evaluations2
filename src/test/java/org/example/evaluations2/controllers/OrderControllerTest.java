package org.example.evaluations2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.evaluations2.dtos.OrderEvent;
import org.example.evaluations2.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldPlaceOrderSuccessfully() throws Exception {

        OrderEvent order = new OrderEvent();
        order.setQuantity(2);
        order.setPrice(500.0);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order placed successfully"));

        Mockito.verify(orderService, Mockito.times(1))
                .createOrder(Mockito.any(OrderEvent.class));
    }

    @Test
    void shouldFailWhenPriceIsZeroOrNegative() throws Exception {

        OrderEvent order = new OrderEvent();
        order.setQuantity(2);
        order.setPrice(0);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Price must be greater than zero"));

        Mockito.verify(orderService, Mockito.never())
                .createOrder(Mockito.any());
    }

    @Test
    void shouldFailWhenQuantityIsZeroOrNegative() throws Exception {

        OrderEvent order = new OrderEvent();
        order.setQuantity(0);
        order.setPrice(100);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Quantity must be greater than zero"));

        Mockito.verify(orderService, Mockito.never())
                .createOrder(Mockito.any());
    }
}