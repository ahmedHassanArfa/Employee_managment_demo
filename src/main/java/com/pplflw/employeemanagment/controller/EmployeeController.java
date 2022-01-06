package com.pplflw.employeemanagment.controller;

import com.pplflw.employeemanagment.constant.StateEnum;
import com.pplflw.employeemanagment.entity.Employee;
import com.pplflw.employeemanagment.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(Integer employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}/change_state")
    public void changeState(@PathVariable Integer employeeId, @RequestParam StateEnum state) {
        employeeService.changeState(employeeId, state);
    }
}
