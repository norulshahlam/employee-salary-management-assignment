package com.shah.employeesalarymanagementassignment.service;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.helper.UploadHelper;
import com.shah.employeesalarymanagementassignment.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Slf4j
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UploadHelper uploadHelper;

    private MultipartFile multipartFile;


    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/employee.csv");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file",
                file.getName(), "text/csv", input);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void uploadUsers() throws IOException {
        employeeService.uploadUsers(multipartFile);
    }

    @Test
    void fetchListOfEmployees() {
        List<Employee> employees = employeeService.fetchListOfEmployees(
                0, 4000, "id", "asc", 0, Long.MAX_VALUE);
        log.info("employees: {}", employees);
    }
}