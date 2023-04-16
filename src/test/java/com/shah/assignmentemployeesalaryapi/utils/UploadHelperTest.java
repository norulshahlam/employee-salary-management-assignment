package com.shah.assignmentemployeesalaryapi.utils;

import com.shah.assignmentemployeesalaryapi.entity.Employee;
import com.shah.assignmentemployeesalaryapi.exception.EmployeeException;
import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import com.shah.assignmentemployeesalaryapi.repository.EmployeeRepository;
import lombok.NoArgsConstructor;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
@Slf4j
@NoArgsConstructor
public class UploadHelperTest {

    @Mock
    private static EmployeeRepository employeeRepository;
    private static List<EmployeeDto> employeeDto = new ArrayList<>();
    @InjectMocks
    private UploadHelper uploadHelper;
    private MultipartFile multipartFile;
    private File file;
    private FileInputStream input;

    @BeforeEach
    void setUp() throws IOException {
        openMocks(this);
        file = new File("src/test/resources/employee.csv");
        input = new FileInputStream(file);
        setUpEmployeeDtoList();
    }

    public static List<EmployeeDto> setUpEmployeeDtoList() {
        employeeDto.add(EmployeeDto.builder()
                .id("001")
                .login("dharry")
                .name("Harry Potter")
                .salary("1234")
                .startDate("2001-11-16")
                .build());
        employeeDto.add(EmployeeDto.builder()
                .id("001")
                .login("dharry")
                .name("de Potter")
                .salary("5567")
                .startDate("11-Nov-11")
                .build());
        return employeeDto;
    }

    @AfterEach
    void tearDown() {
        employeeDto.clear();
    }

    @Test
    void checkFileEmpty_NoFileUploaded() {
        assertThrows(EmployeeException.class, () -> UploadHelper.checkFileEmpty(null));
    }

    @Test
    void checkFileEmpty_EmptyFile() {
        multipartFile = new MockMultipartFile("file",
                file.getName(), "text/csv", (byte[]) null);
        assertThrows(EmployeeException.class, () -> UploadHelper.checkFileEmpty(multipartFile));
    }

    @Test
    void checkFileEmpty_InvalidCsvType() throws IOException {
        multipartFile = new MockMultipartFile("file",
                file.getName(), "text/test", input);
        assertThrows(EmployeeException.class, () -> UploadHelper.checkFileEmpty(multipartFile));
    }

    @Test
    void employeeValidator() {
        employeeDto.forEach(i -> i.setSalary(""));
        assertThrows(EmployeeException.class, () -> UploadHelper.employeeValidator(employeeDto));
    }

    @Test
    void findDuplicateId() {
        assertThrows(EmployeeException.class, () -> UploadHelper.findDuplicateId(employeeDto));
    }

    @Test
    void findDuplicateLogin() {
        assertThrows(EmployeeException.class, () -> UploadHelper.findDuplicateLogin(employeeDto));
    }

    @Test
    void ignoreRows() {
        UploadHelper.ignoreRows(employeeDto);
    }

    @Test
    void findDuplicateLoginInDb() {
        when(employeeRepository.findDistinctByLogin(any()))
                .thenReturn(Employee.builder().id("d").login("dharry").build());
        assertThrows(EmployeeException.class, () -> uploadHelper.findDuplicateLoginInDb(employeeDto));
    }
}