package com.pplflw.employeemanagment.configuration;

import com.pplflw.employeemanagment.constant.StateEnum;
import com.pplflw.employeemanagment.constant.StateEventsEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

@Configuration
@EnableStateMachineFactory
public class SimpleEnumStateMachineConfiguration extends StateMachineConfigurerAdapter<StateEnum, StateEventsEnum> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<StateEnum, StateEventsEnum> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListenerAdapter<>());
    }

    @Override
    public void configure(StateMachineStateConfigurer<StateEnum, StateEventsEnum> states) throws Exception {
        states
                .withStates()
                .initial(StateEnum.ADDED)
                .state(StateEnum.IN_CHECK)
                .state(StateEnum.APPROVED)
                .end(StateEnum.ACTIVE);

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<StateEnum, StateEventsEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(StateEnum.ADDED).target(StateEnum.IN_CHECK).event(StateEventsEnum.IN_CHECK_EVENT)
                .and().withExternal()
                .source(StateEnum.IN_CHECK).target(StateEnum.APPROVED).event(StateEventsEnum.APPROVED_EVENT)
                .and().withExternal()
                .source(StateEnum.APPROVED).target(StateEnum.ACTIVE).event(StateEventsEnum.ACTIVE_EVENT);
    }
}