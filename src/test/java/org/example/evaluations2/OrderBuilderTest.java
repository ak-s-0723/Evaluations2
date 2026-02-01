package org.example.evaluations2;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderBuilderTest {

    @Test
    void shouldCreateOrderSuccessfully() {
        Order order = Order.builder()
                .orderId("ORD-101")
                .customerName("Anurag")
                .quantity(2)
                .price(1000)
                .discount(100)
                .couponCode("NEWUSER")
                .giftWrap(true)
                .build();

        assertEquals("ORD-101", order.getOrderId());
        assertEquals("Anurag", order.getCustomerName());
        assertEquals(2, order.getQuantity());
        assertEquals(1000, order.getPrice());
        assertEquals(100, order.getDiscount());
        assertEquals("NEWUSER", order.getCouponCode());
        assertTrue(order.isGiftWrap());
    }

    @Test
    void shouldThrowExceptionWhenOrderIdIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Order.builder()
                        .customerName("Anurag")
                        .quantity(1)
                        .price(100)
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenOrderIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                Order.builder()
                        .orderId("   ")
                        .customerName("Anurag")
                        .quantity(1)
                        .price(100)
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Order.builder()
                        .orderId("ORD-102")
                        .quantity(1)
                        .price(100)
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                Order.builder()
                        .orderId("ORD-103")
                        .customerName("Anurag")
                        .quantity(0)
                        .price(100)
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                Order.builder()
                        .orderId("ORD-104")
                        .customerName("Anurag")
                        .quantity(1)
                        .price(-10)
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                Order.builder()
                        .orderId("ORD-105")
                        .customerName("Anurag")
                        .quantity(1)
                        .price(100)
                        .discount(-5)
                        .build()
        );
    }

    @Test
    void shouldAllowOptionalFieldsToBeAbsent() {
        Order order = Order.builder()
                .orderId("ORD-106")
                .customerName("Anurag")
                .quantity(1)
                .price(100)
                .build();

        assertNull(order.getCouponCode());
        assertEquals(0, order.getDiscount());
        assertFalse(order.isGiftWrap());
    }
}
