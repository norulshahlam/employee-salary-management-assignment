package com.shah.assignmentemployeesalaryapi.service;

import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author NORUL
 */

public interface EmployeeService {

    List<EmployeeDto> uploadEmployees(MultipartFile file) throws IOException;

    List<EmployeeDto> getEmployeesByParam(
            double minSalary, double maxSalary, String sortedBy, String sortDirection, long offset, long limit);

    String createEmployee(EmployeeDto dto);

    EmployeeDto getEmployeeById(String id);

    String updateEmployeeById(String id, EmployeeDto dto);

    String deleteEmployeeById(String id);
}
