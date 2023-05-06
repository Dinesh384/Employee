package com.org.employee.controller;

import com.org.employee.bean.EmployeeBean;
import com.org.employee.entity.Employee;
import com.org.employee.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeServiceImpl employeeService;

    @PostMapping("/saveEmployeeDetails")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployeeDetails(employee);
    }

    @GetMapping("/getEmployeeDetails/{id}")
    public EmployeeBean getEmployeeResponse(@PathVariable String id){
        EmployeeBean employeeDetails = employeeService.calculateTax(id);
        return employeeDetails;
    }
}