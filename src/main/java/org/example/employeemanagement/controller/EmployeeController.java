package org.example.employeemanagement.controller;

import org.example.employeemanagement.entity.Employee;
import org.example.employeemanagement.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilityService utilityService;

    private final List<Employee> employeeList = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee) {
        String employeeId = utilityService.generateAutoId();
        newEmployee.setId(employeeId);
        newEmployee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
        employeeList.add(newEmployee);
        return ResponseEntity.ok(newEmployee);
    }

    @GetMapping
    public  ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeList);
    }
}
