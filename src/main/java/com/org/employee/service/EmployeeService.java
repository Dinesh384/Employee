package com.org.employee.service;

import com.org.employee.bean.EmployeeBean;
import com.org.employee.entity.Employee;

public interface EmployeeService {

    Employee addEmployeeDetails(Employee employee);

    EmployeeBean calculateTax(String employeeId);
}
