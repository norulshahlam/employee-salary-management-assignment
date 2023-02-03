package com.shah.employeesalarymanagementassignment.service;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.helper.CsvHelper;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import com.shah.employeesalarymanagementassignment.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static com.shah.employeesalarymanagementassignment.helper.EmployeeHelper.*;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void upload(MultipartFile file) throws IOException, ParseException {
        log.info("Uploading employee..");

        // check if file is empty
        checkFileEmpty(file);

        // check if the file is empty, correct filename & format - ok
        List<EmployeeDto> dto = CsvHelper.csvParser(file);
        // check if date & salary is correct format
        employeeValidator(dto);
        // check for duplicate id - throw error if exists - ok
        findDuplicates(dto);
        // skip if contains '#'
        List<EmployeeDto> dto2 = ignoreRows(dto);
        // map dto to employee list
        List<Employee> employees = mapToEmployee(dto2);
        // replace if id exists, else create new employee
        Iterable<Employee> savedEmployees = employeeRepository.saveAll(employees);
        log.info("Uploading employee success: {}", savedEmployees);
    }
}
