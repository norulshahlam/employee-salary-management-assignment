package com.shah.assignmentemployeesalaryapi.controller;

import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import com.shah.assignmentemployeesalaryapi.model.EmployeeResponse;
import com.shah.assignmentemployeesalaryapi.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;

import static com.shah.assignmentemployeesalaryapi.model.EmployeeResponse.SuccessResponse;

/**
 * @author NORUL
 */
@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping(EmployeeController.API)
@CrossOrigin(origins = {"http://localhost:3000"})
public class EmployeeController {

    public static final String USERS_ID = "/users/{id}";
    public static final String USERS = "/users";
    public static final String USERS_UPLOAD = "/users/upload";
    public static final String API = "/api";
    private EmployeeService employeeService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "upload employees using file system")
    @PostMapping(USERS_UPLOAD)
    public EmployeeResponse<List<EmployeeDto>> uploadEmployees(@RequestParam(name = "file")
                                                               MultipartFile file) throws IOException {
        log.info("EmployeeController::uploadUsers");
        List<EmployeeDto> upload = employeeService.uploadEmployees(file);
        return SuccessResponse(upload);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(USERS)
    @Operation(summary = "get Employees By Param")
    public EmployeeResponse<List<EmployeeDto>> getEmployeesByParam(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "offset cannot be negative")
            long offset,
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "limit cannot be negative")
            long limit,
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "minSalary cannot be negative")
            double minSalary,
            @RequestParam(defaultValue = "4000")
            @Positive(message = "maxSalary must be more than zero")
            double maxSalary,
            @RequestParam(defaultValue = "id")
            String sortedBy,
            @RequestParam(defaultValue = "ASC")
            String sortDirection) {
        log.info("EmployeeController::getEmployeesWithParam");
        List<EmployeeDto> employeeList = employeeService.getEmployeesByParam(
                minSalary,
                maxSalary,
                sortedBy,
                sortDirection,
                offset,
                limit);
        return SuccessResponse(employeeList);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(USERS)
    @Operation(summary = "create Employee")
    public EmployeeResponse<String> createEmployee(@RequestBody EmployeeDto dto) {
        log.info("EmployeeController::createEmployee");
        String response = employeeService.createEmployee(dto);
        return SuccessResponse(response);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(USERS_ID)
    @Operation(summary = "get Employee by id")
    public EmployeeResponse<EmployeeDto> getEmployeeById(@PathVariable String id) {
        log.info("EmployeeController::getEmployeeById");
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return SuccessResponse(employee);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update Employee by id")
    @RequestMapping(value = USERS_ID, method = {RequestMethod.PATCH, RequestMethod.PUT})
    public EmployeeResponse<String> updateEmployeeById(@PathVariable String id, @RequestBody EmployeeDto dto) {
        log.info("EmployeeController::updateEmployeeById");
        String employee = employeeService.updateEmployeeById(id, dto);
        return SuccessResponse(employee);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(USERS_ID)
    @Operation(summary = "delete Employee by id")
    public EmployeeResponse<String> deleteEmployeeById(@PathVariable String id) {
        log.info("EmployeeController::deleteEmployeeById");
        String employee = employeeService.deleteEmployeeById(id);
        return SuccessResponse(employee);
    }
}
