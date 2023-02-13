package com.shah.employeesalarymanagementassignment.service;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.exception.EmployeeException;
import com.shah.employeesalarymanagementassignment.utils.UploadHelper;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shah.employeesalarymanagementassignment.utils.CsvHelper.csvParser;
import static com.shah.employeesalarymanagementassignment.utils.MyMapper.mapToEmployee;
import static com.shah.employeesalarymanagementassignment.utils.MyMapper.mapToEmployeeDto;
import static com.shah.employeesalarymanagementassignment.utils.UploadHelper.*;
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
    public static final String EMPLOYEE_NOT_FOUND = "Employee not found";
    private EmployeeRepository employeeRepository;
    private UploadHelper uploadHelper;

    public List<EmployeeDto> uploadEmployees(MultipartFile file) throws IOException {
        log.info("Uploading employee..");

        // check if file is empty, check correct filename & format
        checkFileEmpty(file);
        // parse csv - ok
        List<EmployeeDto> dto = csvParser(file);
        // check if date & salary is correct format - ok
        employeeValidator(dto);
        // check for duplicate id in dto - throw error if exists
        findDuplicateId(dto);
        // check for duplicate login in dto - throw error if exists
        findDuplicateLogin(dto);
        // skip if contains '#'
        ignoreRows(dto);
        // check for duplicate login in db
        uploadHelper.findDuplicateLoginInDb(dto);
        // map dto to employee list
        List<Employee> employees = mapToEmployee(dto);
        // once all is checked, then save to database
        Iterable<Employee> savedEmployees = employeeRepository.saveAll(employees);
        log.info("Uploading employee success: {}", savedEmployees);
        List<Employee> employees1 = IterableUtils.toList(savedEmployees);
        return mapToEmployeeDto(employees1);
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
    public List<Employee> getListOfEmployees(
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

    public String uploadEmployee(EmployeeDto dto) {
        Optional<Employee> employee = employeeRepository.findById(dto.getId());
        if (employee.isPresent()) {
            throw new EmployeeException("Employee ID already exists", null);
        }

        employeeValidator(Collections.singletonList(dto));
        uploadHelper.findDuplicateLoginInDb(Collections.singletonList(dto));
        List<Employee> employees = mapToEmployee(Collections.singletonList(dto));
        employeeRepository.save(employees.get(0));

        return "Successfully created";
    }

    public EmployeeDto getEmployeeById(String id) {
        Employee byId = employeeRepository.findById(id).orElseThrow(() -> new EmployeeException(EMPLOYEE_NOT_FOUND, null));
        return mapToEmployeeDto(List.of(byId)).get(0);
    }

    public String updateEmployeeById(String id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeException(EMPLOYEE_NOT_FOUND, null));
        dto.setId(employee.getId());
        employeeValidator(List.of(dto));
        List<Employee> employees = mapToEmployee(Collections.singletonList(dto));
        employeeRepository.saveAll(employees);
        return "Successfully updated";
    }

    public String deleteEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeException(EMPLOYEE_NOT_FOUND, null));
        employeeRepository.deleteById(employee.getId());
        return "Successfully deleted";
    }
}
