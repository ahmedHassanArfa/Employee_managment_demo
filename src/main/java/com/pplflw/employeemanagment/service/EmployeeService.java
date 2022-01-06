package com.pplflw.employeemanagment.service;

import com.pplflw.employeemanagment.constant.StateEnum;
import com.pplflw.employeemanagment.constant.StateEventsEnum;
import com.pplflw.employeemanagment.entity.Employee;
import com.pplflw.employeemanagment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    private StateMachine<StateEnum, StateEventsEnum> stateMachine;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        stateMachine.start();
        employee.setState(StateEnum.ADDED);
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
        switch (state) {
            case IN_CHECK:
                stateMachine.sendEvent(StateEventsEnum.IN_CHECK_EVENT);
                break;
            case APPROVED:
                stateMachine.sendEvent(StateEventsEnum.APPROVED_EVENT);
                break;
            case ACTIVE:
                stateMachine.sendEvent(StateEventsEnum.ACTIVE_EVENT);
                stateMachine.stop();
                break;
        }
        Employee employee = employeeRepository.getById(employeeId);
        employee.setState(state);
        employeeRepository.save(employee);
    }

}
