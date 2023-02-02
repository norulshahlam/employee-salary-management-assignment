package com.shah.employeesalarymanagementassignment.service;

import com.shah.employeesalarymanagementassignment.helper.CsvHelper;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import com.shah.employeesalarymanagementassignment.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.shah.employeesalarymanagementassignment.helper.EmployeeHelper.checkFileEmpty;
import static com.shah.employeesalarymanagementassignment.helper.EmployeeHelper.findDuplicates;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void upload(MultipartFile file) throws IOException {
        log.info("Uploading employee..");

        // check if file is empty
        checkFileEmpty(file);


        // check if the file is empty, correct filename & format - ok
        // check for duplicate id - throw error if exists - ok
        // check if date & salary is correct format
        // skip if contains '#'
        // check is value meet business requirements
        // replace if id exists, else create new employee
        List<EmployeeDto> dto = CsvHelper.csvParser(file);
        findDuplicates(dto);
        log.info("Uploading employee success: {}", dto);
    }
}
