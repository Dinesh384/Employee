package com.org.employee.service;

import com.org.employee.bean.EmployeeBean;
import com.org.employee.entity.Employee;
import com.org.employee.exception.FieldNotFoundException;
import com.org.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployeeDetails(Employee employee) {
        if (hasMandatoryFields(employee)) {
            employeeRepository.save(employee);
        } else {
            throw new FieldNotFoundException();
        }
        return employee;
    }

    @Override
    public EmployeeBean calculateTax(String employeeId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }
        LocalDate startOfFinancialYear = LocalDate.now().withMonth(4).withDayOfMonth(1);
        LocalDate endOfFinancialYear = startOfFinancialYear.plusYears(1);
        double totalSalary = calculateTotalSalary(employee, startOfFinancialYear, endOfFinancialYear);
        double tax = calculateTaxAmount(totalSalary);
        double cess = calculateCess(totalSalary);

        EmployeeBean employeeBean = EmployeeBean.builder().employeeCode(employee.getId())
                .firstName(employee.getFirstName()).lastName(employee.getLastName())
                .yearlySalary(totalSalary).taxAmount(tax).cessAmount(cess).build();
        return employeeBean;
    }

    private boolean hasMandatoryFields(Employee employee) {
        return (employee.getId() != null && employee.getFirstName() != null && employee.getLastName() != null
                && employee.getDoj() != null && employee.getSalary() != 0.0 && employee.getEmail() != null
                && employee.getPhoneNumber() != null);
    }

    private double calculateTotalSalary(Employee employee, LocalDate start, LocalDate end) {
        LocalDate doj = employee.getDoj();
        if (doj.isAfter(end)) {
            return 0.0;
        }

        double lossOfPayPerDay = employee.getSalary() / 30;
        double totalSalary = employee.getSalary() * ChronoUnit.MONTHS.between(doj.withDayOfMonth(1), end.withDayOfMonth(1)) + employee.getSalary();
        int workingDays = (int) ChronoUnit.DAYS.between(start, end) + 1;
        double totalLossOfPay = lossOfPayPerDay * (workingDays - ChronoUnit.DAYS.between(doj, start));
        return totalSalary - totalLossOfPay;
    }

    private double calculateTaxAmount(double salary) {
        double tax = 0;
        if (salary > 250000 && salary <= 500000) {
            tax = salary * 0.05;
        } else if (salary > 500000 && salary <= 1000000) {
            tax = salary * 0.10;
        } else if (salary > 1000000) {
            tax = salary * 0.20;
        }
        return tax;
    }

    private double calculateCess(double salary) {
        if (salary <= 2500000) {
            return 0.0;
        }
        double cessAmount = (salary - 2500000) * 0.02;
        return cessAmount;
    }
}
