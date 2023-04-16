package com.shah.assignmentemployeesalaryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findDistinctByLogin() {
        employeeRepository.findDistinctByLogin("shah");
    }

    @Test
    void salaryGreaterThan() {
        EmployeeRepository.salaryGreaterThan(0);
    }

    @Test
    void salaryLessThanOrEqualTo() {
        EmployeeRepository.salaryLessThanOrEqualTo(1000);
    }
}