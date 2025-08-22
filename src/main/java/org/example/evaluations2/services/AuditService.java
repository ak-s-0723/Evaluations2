package org.example.evaluations2.services;

import org.springframework.stereotype.Service;

@Service
public class AuditService {
    public void print(String message) {
        System.out.println(message);
    }
}
