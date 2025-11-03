package org.example.employeemanagement.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilityService {
    public String generateAutoId() {
        return UUID.randomUUID().toString();
    }
}
