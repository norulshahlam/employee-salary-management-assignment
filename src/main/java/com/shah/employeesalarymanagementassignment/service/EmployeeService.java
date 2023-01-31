package com.shah.employeesalarymanagementassignment.service;

import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import com.shah.employeesalarymanagementassignment.helper.CsvHelper;
import com.shah.employeesalarymanagementassignment.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void upload(MultipartFile file) throws IOException {
        log.info("Uploading employee..");

        // check if value is correct format
        // check for duplicate id - throw error if exists
        // skip if contains '#'
        // check is value meet business requirements
        // replace if id exists, else create new employee
        List<EmployeeDto> dto = CsvHelper.csvParser(file);
        log.info("Uploading employee success: {}",dto);
    }
}
