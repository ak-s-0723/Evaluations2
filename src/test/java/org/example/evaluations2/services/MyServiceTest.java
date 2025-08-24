package org.example.evaluations2.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyServiceTest {
    MyService service = new MyService();

    @Test
    void testAdd() {
        assertEquals(5, service.add(2, 3));
    }

    @Test
    void testIsPositive() {
        assertTrue(service.isPositive(10));
        assertFalse(service.isPositive(-1));
    }

}