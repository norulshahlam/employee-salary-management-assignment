package com.shah.employeesalarymanagementassignment.controller;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.model.EmployeeResponse;
import com.shah.employeesalarymanagementassignment.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.shah.employeesalarymanagementassignment.model.EmployeeResponse.SuccessResponse;

/**
 * @author NORUL
 */
@RestController
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("users/upload")
    public EmployeeResponse uploadUsers(@RequestParam(name = "file", required = false)
                                        MultipartFile file) throws IOException {
        log.info("EmployeeController::uploadUsers");
        List<Employee> upload = employeeService.uploadUsers(file);
        return SuccessResponse(upload);
    }

    @GetMapping("users")
    public EmployeeResponse fetchListOfEmployees(
            @RequestParam(defaultValue = "0") long offset,
            @RequestParam(defaultValue = "0") long limit,
            @RequestParam(defaultValue = "0") double minSalary,
            @RequestParam(defaultValue = "4000") double maxSalary,
            @RequestParam(defaultValue = "id") String sortedBy,
            @RequestParam(defaultValue = "") String query) {
        log.info("EmployeeController::fetchListOfEmployees");
        List<Employee> employeeList = employeeService.fetchListOfEmployees(
                minSalary, maxSalary, sortedBy, query, offset, limit);
        return SuccessResponse(employeeList);
    }
}
