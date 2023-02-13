package com.shah.employeesalarymanagementassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import com.shah.employeesalarymanagementassignment.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.shah.employeesalarymanagementassignment.controller.EmployeeController.USERS_UPLOAD;
import static com.shah.employeesalarymanagementassignment.utils.UploadHelperTest.setUpEmployees;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMultipartFile multipartFile;
    private static List<EmployeeDto> employeeDto = new ArrayList<>();
    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() throws IOException {
        openMocks(this);
        File file = new File("src/test/resources/employee.csv");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file",
                file.getName(), "text/csv", input);
        employeeDto = setUpEmployees();
    }

    @Test
    void uploadUsers() throws Exception {

        when(employeeService.uploadEmployees(any())).thenReturn(employeeDto);
        mockMvc.perform(multipart(USERS_UPLOAD)
                        .file(multipartFile))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

    @Test
    void getListOfEmployees() {
    }

    @Test
    void uploadEmployee() {
    }

    @Test
    void getEmployeeById() {
    }

    @Test
    void updateEmployeeById() {
    }

    @Test
    void deleteEmployeeById() {
    }
}