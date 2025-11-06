package org.example.employeemanagement.service;

import org.example.employeemanagement.entity.Department;
import org.example.employeemanagement.entity.Employee;
import org.example.employeemanagement.entity.dto.EmployeeCreateRequest;
import org.example.employeemanagement.exception.AppException;
import org.example.employeemanagement.exception.ErrorCode;
import org.example.employeemanagement.repository.DepartmentRepository;
import org.example.employeemanagement.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Employee createEmployee(EmployeeCreateRequest employeeCreateRequest) {
        if (employeeRepository.existsByEmail(employeeCreateRequest.getEmail())) {
            throw new AppException(ErrorCode.EMPLOYEE_EXISTS);
        }

        Employee newEmployee = new Employee();

        newEmployee.setName(employeeCreateRequest.getName());
        newEmployee.setEmail(employeeCreateRequest.getEmail());

        departmentRepository.findById(employeeCreateRequest.getDepartmentId())
                .ifPresent(newEmployee::setDepartment);

        Employee savedEmployee = employeeRepository.save(newEmployee);
        logger.info("Một nhân viên mới đã được thêm: ID={}, Tên={}", savedEmployee.getId(), savedEmployee.getName());

        return savedEmployee;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }

    public Employee updateEmployee(Long id, EmployeeCreateRequest employee) {
        Employee existedEmloyee = employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new AppException(ErrorCode.EMPLOYEE_EXISTS);
        }

        existedEmloyee.setName(employee.getName());
        existedEmloyee.setEmail(employee.getEmail());

        departmentRepository.findById(employee.getDepartmentId())
                .ifPresent(existedEmloyee::setDepartment);

        Employee updatedEmployee = employeeRepository.save(existedEmloyee);
        logger.warn("Thông tin nhân viên ID={} đã được cập nhật.", updatedEmployee.getId());

        return updatedEmployee;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        logger.warn("Đã xóa nhân viên với ID: {}", id);
    }

    public Employee getEmployeeByName(String employeeName) {
        return employeeRepository.findByName(employeeName).orElse(null);
    }

    public List<Employee> getEmployeeByDepartment(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName);
    }

    public List<Employee> searchEmployees(String keyword, String searchBy) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll();
        }

        if ("department".equals(searchBy)) {
            return employeeRepository.findByDepartmentName(keyword);
        } else {
            return employeeRepository.findByNameContainingIgnoreCase(keyword);
        }
    }
}
