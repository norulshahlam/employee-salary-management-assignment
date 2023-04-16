package com.shah.assignmentemployeesalaryapi.utils;

import com.shah.assignmentemployeesalaryapi.exception.EmployeeException;
import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.shah.assignmentemployeesalaryapi.utils.UploadHelperTest.setUpEmployeeDtoList;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Slf4j
class MyMapperTest {

    private List<EmployeeDto> employeeDto = new ArrayList<>();

    @BeforeEach
    void setUp() {
        employeeDto = setUpEmployeeDtoList();
    }

    @Test
    void mapToEmployee() {
        MyMapper.mapToEmployee(employeeDto);
    }

    @Test
    void mapToEmployee_InvalidDateFormat() {
        employeeDto.add(EmployeeDto.builder()
                .id("001")
                .login("dharry")
                .name("Harry Potter")
                .salary("1234")
                .startDate("2001-99-16")
                .build());
        assertThrows(EmployeeException.class, () -> MyMapper.mapToEmployee(employeeDto));
    }

    @AfterEach
    void tearDown() {
        employeeDto.clear();
    }
}