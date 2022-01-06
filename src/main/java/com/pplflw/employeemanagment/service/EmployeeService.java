package com.pplflw.employeemanagment.service;

import com.pplflw.employeemanagment.constant.StateEnum;
import com.pplflw.employeemanagment.entity.Employee;
import com.pplflw.employeemanagment.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        employee = employeeRepository.save(employee);
        return employee;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    public void changeState(Integer employeeId, StateEnum state) {
        Employee employee = employeeRepository.getById(employeeId);
        employee.setState(state);
        employeeRepository.save(employee);
    }

}
