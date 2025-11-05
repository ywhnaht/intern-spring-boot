package org.example.employeemanagement.controller;

import jakarta.validation.Valid;
import org.example.employeemanagement.entity.Employee;
import org.example.employeemanagement.entity.dto.ApiResponse;
import org.example.employeemanagement.entity.dto.EmployeeCreateRequest;
import org.example.employeemanagement.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeApiController {
    private final EmployeeService employeeService;

    public EmployeeApiController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody @Valid EmployeeCreateRequest employeeCreateRequest) {
        Employee employee = employeeService.createEmployee(employeeCreateRequest);
        return ResponseEntity.ok(ApiResponse.success(employee, "Thêm nhân viên thành công"));
    }

    @GetMapping()
    public  ResponseEntity<ApiResponse<?>> getEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String departmentName
    ) {
        if (name != null) {
            Employee employee = employeeService.getEmployeeByName(departmentName);
            return ResponseEntity.ok(ApiResponse.success(employee, "Tìm thấy nhân viên với tên " + name));
        }

        if  (departmentName != null) {
            List<Employee> employees = employeeService.getEmployeeByDepartment(departmentName);
            return ResponseEntity.ok(ApiResponse.success(employees, "Tìm thấy nhân viên ở phòng ban " + departmentName));
        }

        return ResponseEntity.ok(ApiResponse.success(employeeService.getAllEmployees(), "Lấy danh sách nhân viên thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(ApiResponse.success(employee, "Tìm thấy nhân viên với id " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeCreateRequest updateEmployee) {
        Employee employee = employeeService.updateEmployee(id, updateEmployee);
        return ResponseEntity.ok(ApiResponse.success(employee, "Cập nhật thông tin nhân viên thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa nhân viên thành công"));
    }
}
