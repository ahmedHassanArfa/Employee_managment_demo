package com.pplflw.employeemanagment.service;

import com.pplflw.employeemanagment.configuration.EmployeeStateChangeInterceptor;
import com.pplflw.employeemanagment.constant.StateEnum;
import com.pplflw.employeemanagment.constant.StateEventsEnum;
import com.pplflw.employeemanagment.entity.Employee;
import com.pplflw.employeemanagment.repository.EmployeeRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final StateMachineFactory<StateEnum, StateEventsEnum> stateMachineFactory;
    private final EmployeeStateChangeInterceptor employeeStateChangeInterceptor;

    public EmployeeService(EmployeeRepository employeeRepository, StateMachineFactory<StateEnum, StateEventsEnum> stateMachineFactory, EmployeeStateChangeInterceptor employeeStateChangeInterceptor) {
        this.employeeRepository = employeeRepository;
        this.stateMachineFactory = stateMachineFactory;
        this.employeeStateChangeInterceptor = employeeStateChangeInterceptor;
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        employee.setId(null);
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

    @Transactional
    public Employee changeState(Integer employeeId, StateEnum state) {
        StateMachine<StateEnum, StateEventsEnum> sm = build(employeeId);
        switch (state) {
            case IN_CHECK:
                sendEvent(employeeId, sm, StateEventsEnum.IN_CHECK_EVENT);
                break;
            case APPROVED:
                sendEvent(employeeId, sm, StateEventsEnum.APPROVED_EVENT);
                break;
            case ACTIVE:
                sendEvent(employeeId, sm, StateEventsEnum.ACTIVE_EVENT);
                break;
        }
        Employee employee = employeeRepository.getById(employeeId);
        return employee;
    }

    private void sendEvent(Integer employeeId, StateMachine<StateEnum, StateEventsEnum> sm, StateEventsEnum event){
        Message msg = MessageBuilder.withPayload(event)
                .setHeader("EMPLOYEE_ID", employeeId)
                .build();

        sm.sendEvent(msg);
    }

    private StateMachine<StateEnum, StateEventsEnum> build(Integer employeeId){
        Employee employee = employeeRepository.findById(employeeId).get();

        StateMachine<StateEnum, StateEventsEnum> sm = stateMachineFactory.getStateMachine(employeeId.toString());

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(employeeStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(employee.getState(), null, null, null));
                });

        sm.start();

        return sm;
    }

}
