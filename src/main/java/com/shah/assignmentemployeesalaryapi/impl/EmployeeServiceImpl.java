package com.shah.assignmentemployeesalaryapi.impl;

import com.shah.assignmentemployeesalaryapi.entity.Employee;
import com.shah.assignmentemployeesalaryapi.exception.EmployeeException;
import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import com.shah.assignmentemployeesalaryapi.repository.EmployeeRepository;
import com.shah.assignmentemployeesalaryapi.service.EmployeeService;
import com.shah.assignmentemployeesalaryapi.utils.UploadHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shah.assignmentemployeesalaryapi.repository.EmployeeRepository.salaryGreaterThan;
import static com.shah.assignmentemployeesalaryapi.repository.EmployeeRepository.salaryLessThanOrEqualTo;
import static com.shah.assignmentemployeesalaryapi.utils.CsvHelper.csvParser;
import static com.shah.assignmentemployeesalaryapi.utils.MyMapper.mapToEmployee;
import static com.shah.assignmentemployeesalaryapi.utils.MyMapper.mapToEmployeeDto;
import static com.shah.assignmentemployeesalaryapi.utils.UploadHelper.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * @author NORUL
 */
@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    public static final String EMPLOYEE_NOT_FOUND = "Employee not found";
    public static final String SUCCESSFULLY_CREATED = "Successfully created";
    public static final String SUCCESSFULLY_UPDATED = "Successfully updated";
    public static final String SUCCESSFULLY_DELETED = "Successfully deleted";
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
     * @param limit         default is 0 - no limit
     * @return list of employees
     */
    public List<EmployeeDto> getEmployeesByParam(
            double minSalary, double maxSalary, String sortedBy, String sortDirection, long offset, long limit) {

        Specification<Employee> specification = where(salaryGreaterThan(minSalary)
                .and(salaryLessThanOrEqualTo(maxSalary)));

        Sort sort = Sort
                .by(Sort.Direction.valueOf(sortDirection.toUpperCase()), sortedBy);

        List<Employee> all = employeeRepository
                .findAll(specification, sort)
                .stream()
                .limit(limit < 1 ? Long.MAX_VALUE : limit)
                .skip(offset)
                .collect(Collectors.toList());
        if (ObjectUtils.isEmpty(all)) {
            throw new EmployeeException("No employee", null);
        }

        List<EmployeeDto> employeeDtoList = mapToEmployeeDto(all);
        all.forEach(i -> log.info("sorted: {}", i));
        return employeeDtoList;
    }

    public String createEmployee(EmployeeDto dto) {
        Optional<Employee> employee = employeeRepository.findById(dto.getId());
        if (employee.isPresent()) {
            throw new EmployeeException("Employee ID already exists", null);
        }

        employeeValidator(Collections.singletonList(dto));
        uploadHelper.findDuplicateLoginInDb(Collections.singletonList(dto));
        List<Employee> employees = mapToEmployee(Collections.singletonList(dto));
        employeeRepository.save(employees.get(0));

        return SUCCESSFULLY_CREATED;
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
        return SUCCESSFULLY_UPDATED;
    }

    public String deleteEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeException(EMPLOYEE_NOT_FOUND, null));
        employeeRepository.deleteById(employee.getId());
        return SUCCESSFULLY_DELETED;
    }
}
