package com.pplflw.employeemanagment.repository;

import com.pplflw.employeemanagment.constant.StateEnum;
import com.pplflw.employeemanagment.entity.Employee;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepositoryImplementation<Employee, Integer> {

}
