package com.org.employee.repository;

import com.org.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {

    @Query(value = "select e from employee_details e where e.id = :id")
    Employee findEmployeeById(String id);
}
