package org.example.employeemanagement.controller;

import org.example.employeemanagement.entity.model.Department;
import org.example.employeemanagement.entity.model.Employee;
import org.example.employeemanagement.entity.dto.EmployeeCreateRequest;
import org.example.employeemanagement.service.DepartmentService;
import org.example.employeemanagement.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeWebController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/list")
    public String showEmployeeList(Model model,
                                   @RequestParam(name = "keyword", required = false) String keyword,
                                   @RequestParam(name = "searchBy", required = false) String searchBy) {
        List<Employee> employeeList = employeeService.searchEmployees(keyword,  searchBy);

        model.addAttribute("employees", employeeList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchBy", searchBy);

        return "employees/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("employee", new EmployeeCreateRequest());
        model.addAttribute("departments", departments);

        return "employees/add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute EmployeeCreateRequest employee) {
        employeeService.createEmployee(employee);

        return "redirect:/employees/list";
    }
}