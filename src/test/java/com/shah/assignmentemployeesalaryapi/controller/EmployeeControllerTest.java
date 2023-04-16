package com.shah.assignmentemployeesalaryapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import com.shah.assignmentemployeesalaryapi.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.shah.assignmentemployeesalaryapi.controller.EmployeeController.*;
import static com.shah.assignmentemployeesalaryapi.impl.EmployeeServiceImpl.*;
import static com.shah.assignmentemployeesalaryapi.model.ResponseStatus.SUCCESS;
import static com.shah.assignmentemployeesalaryapi.utils.UploadHelperTest.setUpEmployeeDtoList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    MultiValueMap<String, String> employeeParams = new LinkedMultiValueMap<>();

    public static MultiValueMap<String, String> employeeParams() {
        MultiValueMap<String, String> employeeParams = new LinkedMultiValueMap<>();
        employeeParams.add("offset", "0");
        employeeParams.add("limit", "1");
        employeeParams.add("minSalary", "0");
        employeeParams.add("maxSalary", "4000");
        employeeParams.add("sortedBy", "id");
        employeeParams.add("sortDirection", "ASC");
        return employeeParams;
    }

    @BeforeEach
    void setUp() throws IOException {

        /** TODO
         * Add test case for ControllerAdvice
         * https://reflectoring.io/spring-boot-web-controller-test/
         **/

        openMocks(this);
        File file = new File("src/test/resources/employee.csv");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file",
                file.getName(), "text/csv", input);
        employeeDto = setUpEmployeeDtoList();
        employeeParams = employeeParams();
    }

    @Test
    void uploadEmployees() throws Exception {
        when(employeeService.uploadEmployees(any()))
                .thenReturn(employeeDto);
        mockMvc.perform(multipart(API + USERS_UPLOAD).file(multipartFile))
                .andDo(print())
                .andExpect(status()
                        .isCreated());
    }

    @Test
    void getListOfEmployees() throws Exception {
        when(employeeService.getEmployeesByParam(
                anyDouble(), anyDouble(), anyString(), anyString(), anyLong(), anyLong()))
                .thenReturn(employeeDto);

        mockMvc.perform(get(API + USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(employeeParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.status").value(SUCCESS.name()));
    }

    @Test
    void uploadEmployee() throws Exception {
        when(employeeService.createEmployee(any(EmployeeDto.class)))
                .thenReturn(SUCCESSFULLY_CREATED);
        String request = objectMapper.writeValueAsString(employeeDto.get(0));
        mockMvc.perform(post(API + USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value(SUCCESSFULLY_CREATED))
                .andExpect(jsonPath("$.status").value(SUCCESS.name()));
    }

    @Test
    void getEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(anyString()))
                .thenReturn(employeeDto.get(0));

        mockMvc.perform(get(API + USERS_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.status").value(SUCCESS.name()));
    }

    @Test
    void updateEmployeeById() throws Exception {
        when(employeeService.updateEmployeeById(anyString(), any(EmployeeDto.class)))
                .thenReturn(SUCCESSFULLY_UPDATED);
        String request = objectMapper.writeValueAsString(employeeDto.get(0));
        mockMvc.perform(put(API + USERS_ID, "1")
                        .content(String.valueOf(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESSFULLY_UPDATED))
                .andExpect(jsonPath("$.status").value(SUCCESS.name()));
    }

    @Test
    void deleteEmployeeById() throws Exception {
        when(employeeService.deleteEmployeeById(anyString()))
                .thenReturn(SUCCESSFULLY_DELETED);
        mockMvc.perform(delete(API + USERS_ID, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESSFULLY_DELETED))
                .andExpect(jsonPath("$.status").value(SUCCESS.name()));
    }
}