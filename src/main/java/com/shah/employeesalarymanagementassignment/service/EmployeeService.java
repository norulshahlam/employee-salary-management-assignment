package com.shah.employeesalarymanagementassignment.service;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.helper.CsvHelper;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import com.shah.employeesalarymanagementassignment.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.shah.employeesalarymanagementassignment.helper.EmployeeHelper.*;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public List<Employee> upload(MultipartFile file) throws IOException {
        log.info("Uploading employee..");

        // check if file is empty
        checkFileEmpty(file);
        // check if the file is empty, correct filename & format - ok
        List<EmployeeDto> dto = CsvHelper.csvParser(file);
        // check if date & salary is correct format
        employeeValidator(dto);
        // check for duplicate id - throw error if exists - ok
        findDuplicateId(dto);
        // check for duplicate login - throw error if exists
        findDuplicateLogin(dto);
        // skip if contains '#'
        List<EmployeeDto> dto2 = ignoreRows(dto);
        // map dto to employee list - ok
        List<Employee> employees = mapToEmployee(dto2);
        // replace if id exists, else create new employee - ok
        Iterable<Employee> savedEmployees = employeeRepository.saveAll(employees);
        log.info("Uploading employee success: {}", savedEmployees);

        return IterableUtils.toList(savedEmployees);
    }


}
