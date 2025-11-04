package org.example.employeemanagement.service;

import org.example.employeemanagement.entity.Department;
import org.example.employeemanagement.entity.Employee;
import org.example.employeemanagement.repository.DepartmentRepository;
import org.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee updatedEmployee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

        updatedEmployee.setName(employee.getName());
        updatedEmployee.setEmail(employee.getEmail());

        if (employee.getDepartment() != null) {
            String departmentName = employee.getDepartment().getName();
            Department department = departmentRepository.findByName(departmentName)
                    .orElseGet(() -> departmentRepository.save(employee.getDepartment()));
            updatedEmployee.setDepartment(department);
        }

        return employeeRepository.save(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee getEmployeeByName(String employeeName) {
        return employeeRepository.findByName(employeeName).orElse(null);
    }

    public List<Employee> getEmployeeByDepartment(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName);
    }
}
