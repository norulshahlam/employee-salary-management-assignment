package com.shah.employeesalarymanagementassignment.service;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.helper.UploadHelper;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import com.shah.employeesalarymanagementassignment.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.shah.employeesalarymanagementassignment.helper.CsvHelper.csvParser;
import static com.shah.employeesalarymanagementassignment.helper.MyMapper.mapToEmployee;
import static com.shah.employeesalarymanagementassignment.helper.UploadHelper.*;
import static com.shah.employeesalarymanagementassignment.repository.EmployeeRepository.salaryGreaterThan;
import static com.shah.employeesalarymanagementassignment.repository.EmployeeRepository.salaryLessThanOrEqualTo;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * @author NORUL
 */
@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private UploadHelper uploadHelper;

    public List<Employee> uploadUsers(MultipartFile file) throws IOException {
        log.info("Uploading employee..");

        // check if file is empty, check correct filename & format - ok
        checkFileEmpty(file);
        // parse csv - ok
        List<EmployeeDto> dto = csvParser(file);
        // check if date & salary is correct format - ok
        employeeValidator(dto);
        // check for duplicate id - throw error if exists - ok
        findDuplicateId(dto);
        // check for duplicate login in dto - throw error if exists
        findDuplicateLogin(dto);
        // skip if contains '#'
        ignoreRows(dto);
        // check for duplicate login in db
        findDuplicateLoginInDb(dto);
        // map dto to employee list - ok
        List<Employee> employees = mapToEmployee(dto);
        // once all is checked, then save to database
        Iterable<Employee> savedEmployees = employeeRepository.saveAll(employees);
        log.info("Uploading employee success: {}", savedEmployees);

        return IterableUtils.toList(savedEmployees);
    }

    /**
     * @param minSalary     default is 0
     * @param maxSalary     default is 4000.00
     * @param sortedBy      default is "id"
     * @param sortDirection default is "ASC"
     * @param offset        default is 0
     * @param limit         default is no limit
     * @return list of employees
     */
    public List<Employee> fetchListOfEmployees(
            double minSalary, double maxSalary, String sortedBy, String sortDirection, long offset, long limit) {

        if (limit == 0) {
            limit = Long.MAX_VALUE;
        }

        Specification<Employee> specification = where(
                salaryGreaterThan(minSalary)
                        .and(salaryLessThanOrEqualTo(maxSalary)));

        Sort sort = Sort
                .by(Direction.valueOf(sortDirection.toUpperCase()), sortedBy);

        List<Employee> all = employeeRepository
                .findAll(specification, sort)
                .stream()
                .limit(limit)
                .skip(offset)
                .collect(Collectors.toList());

        all.forEach(i -> log.info("sorted: {}", i));
        return all;
    }
}
