package com.org.employee.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeBean {

    private String employeeCode;

    private String firstName;

    private String lastName;

    private double yearlySalary;

    private double taxAmount;

    private double cessAmount;
}