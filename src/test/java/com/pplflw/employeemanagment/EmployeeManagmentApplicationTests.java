package com.pplflw.employeemanagment;

import com.pplflw.employeemanagment.constant.StateEnum;
import com.pplflw.employeemanagment.entity.Employee;
import com.pplflw.employeemanagment.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeManagmentApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Test
    void createEmployee() {
        Employee employee = saveEmployee();
        assert employee.getId() != null;
        assert employee.getState() == StateEnum.ADDED;
    }

    @Test
    void changeState() {
        Employee employee = saveEmployee();

        employee = employeeService.changeState(employee.getId(), StateEnum.IN_CHECK);
        assert employee.getState() == StateEnum.IN_CHECK;

        employee = employeeService.changeState(employee.getId(), StateEnum.APPROVED);
        assert employee.getState() == StateEnum.APPROVED;

        employee = employeeService.changeState(employee.getId(), StateEnum.ACTIVE);
        assert employee.getState() == StateEnum.ACTIVE;
    }

    Employee saveEmployee() {
        Employee employee = new Employee();
        employee.setName("testName");
        employee.setAge(20);
        employee.setContact("contactName");
        employee = employeeService.createEmployee(employee);
        return employee;
    }


}
