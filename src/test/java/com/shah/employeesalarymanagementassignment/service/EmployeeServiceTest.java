package com.shah.employeesalarymanagementassignment.service;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.utils.UploadHelper;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
    private EmployeeDto dto;
    private Employee employee;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/employee.csv");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file",
                file.getName(), "text/csv", input);

        dto = EmployeeDto.builder()
                .id("p0001")
                .login("anpch@example.com")
                .name("Shah")
                .salary("99")
                .startDate("2020-01-01")
                .build();

        employee = Employee.builder()
                .id("p0001")
                .login("anpch@example.com")
                .name("Shah")
                .salary(99L)
                .startDate(LocalDate.now())
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void uploadEmployees() throws IOException {
        employeeService.uploadEmployees(multipartFile);
    }

    @Test
    void getListOfEmployees() {
        List<EmployeeDto> employees = employeeService.getEmployeesByParam(
                0, 4000, "id", "asc", 0, Long.MAX_VALUE);
        log.info("employees: {}", employees);
    }

    @Test
    void uploadEmployee() {
        employeeService.createEmployee(dto);
        when(employeeRepository.findById(anyString())).thenReturn(Optional.empty());
        employeeService.createEmployee(dto);
    }

    @Test
    void getEmployeeById() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.ofNullable(employee));
        employeeService.getEmployeeById("p0001");
    }

    @Test
    void updateEmployeeById() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.ofNullable(employee));
        employeeService.updateEmployeeById(dto.getId(), dto);
    }

    @Test
    void deleteEmployeeById() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.ofNullable(employee));
        employeeService.deleteEmployeeById(dto.getId());
    }
}