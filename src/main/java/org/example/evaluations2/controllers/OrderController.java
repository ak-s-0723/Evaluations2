package org.example.evaluations2.controllers;


import org.example.evaluations2.dtos.OrderEvent;
import org.example.evaluations2.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderEvent order) {
        if (order.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        orderService.createOrder(order);
        return ResponseEntity.ok("Order placed successfully");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleRequestErrors(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

