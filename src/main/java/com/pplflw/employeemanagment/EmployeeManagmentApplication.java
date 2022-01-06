package com.pplflw.employeemanagment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Employees Management", version = "1.0", description = "Employees Management"))
public class EmployeeManagmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagmentApplication.class, args);
    }

}
