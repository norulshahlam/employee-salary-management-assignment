package com.shah.employeesalarymanagementassignment.repository;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findDistinctByLogin(String login);

}
