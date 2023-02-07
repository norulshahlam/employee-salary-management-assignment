package com.shah.employeesalarymanagementassignment.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {

    }

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