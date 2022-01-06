package com.pplflw.employeemanagment.configuration;

import com.pplflw.employeemanagment.constant.StateEnum;
import com.pplflw.employeemanagment.constant.StateEventsEnum;
import com.pplflw.employeemanagment.entity.Employee;
import com.pplflw.employeemanagment.repository.EmployeeRepository;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class EmployeeStateChangeInterceptor  extends StateMachineInterceptorAdapter<StateEnum, StateEventsEnum> {

    private final EmployeeRepository employeeRepository;

    public EmployeeStateChangeInterceptor(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @Override
    public void preStateChange(State<StateEnum, StateEventsEnum> state, Message<StateEventsEnum> message,
                               Transition<StateEnum, StateEventsEnum> transition, StateMachine<StateEnum, StateEventsEnum> stateMachine) {

        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Integer.class.cast(msg.getHeaders().getOrDefault("EMPLOYEE_ID", -1L)))
                    .ifPresent(empoyeeId -> {
                        Employee employee = employeeRepository.findById(empoyeeId).get();
                        employee.setState(state.getId());
                        employeeRepository.save(employee);
                    });
        });
    }

}
