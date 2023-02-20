package com.shah.employeesalarymanagementassignment.controller;

import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import com.shah.employeesalarymanagementassignment.model.EmployeeResponse;
import com.shah.employeesalarymanagementassignment.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.shah.employeesalarymanagementassignment.model.EmployeeResponse.SuccessResponse;

/**
 * @author NORUL
 */
@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"})
public class EmployeeController {

    public static final String USERS_ID = "/users/{id}";
    public static final String USERS = "/users";
    public static final String USERS_UPLOAD = "/users/upload";
    private EmployeeService employeeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(USERS_UPLOAD)
    public EmployeeResponse<List<EmployeeDto>> uploadEmployees(@RequestParam(name = "file")
                                                               MultipartFile file) throws IOException {
        log.info("EmployeeController::uploadUsers");
        List<EmployeeDto> upload = employeeService.uploadEmployees(file);
        return SuccessResponse(upload);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(USERS)
    public EmployeeResponse<List<EmployeeDto>> getEmployeesByParam(
            @RequestParam(defaultValue = "0") long offset,
            @RequestParam(defaultValue = "0") long limit,
            @RequestParam(defaultValue = "0") double minSalary,
            @RequestParam(defaultValue = "4000") double maxSalary,
            @RequestParam(defaultValue = "id") String sortedBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        log.info("EmployeeController::getEmployeesWithParam");
        List<EmployeeDto> employeeList = employeeService.getEmployeesByParam(
                minSalary, maxSalary, sortedBy, sortDirection, offset, limit);
        return SuccessResponse(employeeList);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(USERS)
    public EmployeeResponse<String> createEmployee(@RequestBody EmployeeDto dto) {
        log.info("EmployeeController::createEmployee");
        String response = employeeService.createEmployee(dto);
        return SuccessResponse(response);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(USERS_ID)
    public EmployeeResponse<EmployeeDto> getEmployeeById(@PathVariable String id) {
        log.info("EmployeeController::getEmployeeById");
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return SuccessResponse(employee);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = USERS_ID, method = {RequestMethod.PATCH, RequestMethod.PUT})
    public EmployeeResponse<String> updateEmployeeById(@PathVariable String id, @RequestBody EmployeeDto dto) {
        log.info("EmployeeController::updateEmployeeById");
        String employee = employeeService.updateEmployeeById(id, dto);
        return SuccessResponse(employee);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(USERS_ID)
    public EmployeeResponse<String> deleteEmployeeById(@PathVariable String id) {
        log.info("EmployeeController::deleteEmployeeById");
        String employee = employeeService.deleteEmployeeById(id);
        return SuccessResponse(employee);
    }
}
