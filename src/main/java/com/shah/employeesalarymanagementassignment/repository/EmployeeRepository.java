package com.shah.employeesalarymanagementassignment.repository;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findDistinctByLogin(String login);
    List<Employee> findBySalaryBetween(double min, double max);

}
