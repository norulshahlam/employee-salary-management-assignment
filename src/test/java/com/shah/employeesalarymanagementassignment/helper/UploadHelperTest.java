package com.shah.employeesalarymanagementassignment.helper;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.exception.EmployeeException;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import com.shah.employeesalarymanagementassignment.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UploadHelperTest {
    @Mock
    private EmployeeRepository employeeRepository;
    private List<EmployeeDto> employeeDto = new ArrayList<>();
    private List<Employee> employee = new ArrayList<>();
    private UploadHelper uploadHelper;
    private MultipartFile multipartFile;
    File file;
    FileInputStream input;

    UploadHelperTest() {
    }

    @BeforeEach
    void setUp() throws IOException {
        file = new File("src/test/resources/employee.csv");
        input = new FileInputStream(file);
        setUpEmployees();
    }

    void setUpEmployees() {
        employeeDto.add(EmployeeDto.builder()
                .id("001")
                .login("dharry")
                .build());
        employeeDto.add(EmployeeDto.builder()
                .id("001")
                .login("dharry")
                .build());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkFileEmpty_NoFileUploaded() {
        Assertions.assertThrows(EmployeeException.class, () -> uploadHelper.checkFileEmpty(null));
    }

    @Test
    void checkFileEmpty_EmptyFile() {
        multipartFile = new MockMultipartFile("file",
                file.getName(), "text/csv", (byte[]) null);
        Assertions.assertThrows(EmployeeException.class, () -> uploadHelper.checkFileEmpty(multipartFile));
    }

    @Test
    void checkFileEmpty_InvalidCsvType() throws IOException {
        multipartFile = new MockMultipartFile("file",
                file.getName(), "text/test", input);
        Assertions.assertThrows(EmployeeException.class, () -> uploadHelper.checkFileEmpty(multipartFile));
    }

    @Test
    void employeeValidator() {
        Assertions.assertThrows(EmployeeException.class, () -> uploadHelper.employeeValidator(employeeDto));
    }

    @Test
    void findDuplicateId() {
        Assertions.assertThrows(EmployeeException.class, () -> uploadHelper.findDuplicateId(employeeDto));
    }

    @Test
    void findDuplicateLogin() {
        Assertions.assertThrows(EmployeeException.class, () -> uploadHelper.findDuplicateLogin(employeeDto));
    }

    @Test
    void ignoreRows() {
        uploadHelper.ignoreRows(employeeDto);
    }

    @Test
    void findDuplicateLoginInDb() {
        employeeDto.forEach(i -> log.info(i.toString()));
        Employee dharry = Employee.builder().login("dharry").build();
        when(employeeRepository.findDistinctByLogin(any()))
                .thenReturn(dharry);
        Assertions.assertThrows(EmployeeException.class, () -> uploadHelper.findDuplicateLoginInDb(employeeDto));
    }
}