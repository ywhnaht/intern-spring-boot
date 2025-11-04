package org.example.employeemanagement.controller;

import org.example.employeemanagement.entity.Employee;
import org.example.employeemanagement.service.EmployeeService;
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
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee) {
        Employee employee = employeeService.createEmployee(newEmployee);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/all")
    public  ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updateEmployee) {
        Employee employee = employeeService.updateEmployee(id, updateEmployee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Delete Employee successfully");
    }

    @GetMapping()
    public ResponseEntity<Employee> getEmployeeByName(@RequestParam String employeeName) {
        Employee employee = employeeService.getEmployeeByName(employeeName);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/department")
    public ResponseEntity<List<Employee>> getEmployeeByDepartment(@RequestParam String departmentName) {
        List<Employee> employees = employeeService.getEmployeeByDepartment(departmentName);
        return ResponseEntity.ok(employees);
    }
}
