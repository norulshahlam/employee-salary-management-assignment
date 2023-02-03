package com.shah.employeesalarymanagementassignment.controller;

import com.shah.employeesalarymanagementassignment.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("users/upload")
    public void uploadUsers(@RequestParam(name = "file", required = false)
                                MultipartFile file) throws IOException, ParseException {
        log.info("EmployeeController::uploadUsers");

        employeeService.upload(file);
    }

}
