package com.shah.assignmentemployeesalaryapi.utils;

import com.shah.assignmentemployeesalaryapi.entity.Employee;
import com.shah.assignmentemployeesalaryapi.exception.EmployeeException;
import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.GenericValidator;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author NORUL
 */
@Slf4j
@AllArgsConstructor
public class MyMapper {

    public static List<Employee> mapToEmployee(List<EmployeeDto> employeeDto) {
        log.info("inside mapToEmployee");
        return employeeDto.parallelStream().map(i -> {
            Employee employee = new Employee();
            employee.setId(i.getId());
            employee.setName(i.getName());
            employee.setLogin(i.getLogin());
            employee.setSalary(Double.parseDouble(i.getSalary()));
            try {
                employee.setStartDate(dateConverter(i));
            } catch (ParseException e) {
                throw new EmployeeException("Invalid Date format: " + e.getMessage(), null);
            }
            return employee;
        }).collect(Collectors.toList());
    }

    private static LocalDate dateConverter(EmployeeDto i) throws ParseException {

        String date = i.getStartDate();
        String pattern1 = "yyyy-MM-dd";
        String pattern2 = "dd-MMM-yy";

        DateTimeFormatter format1 = DateTimeFormatter.ofPattern(pattern1);
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern(pattern2);

        if (GenericValidator.isDate(date, pattern1, true)) {
            return LocalDate.parse(date, format1);
        }
        if (GenericValidator.isDate(date, pattern2, true)) {
            return LocalDate.parse(date, format2);
        }
        throw new EmployeeException("Invalid Date format for id " + i.getId(), i.getStartDate());
    }

    public static List<EmployeeDto> mapToEmployeeDto(List<Employee> employee) {
        log.info("inside mapToEmployeeDto");
        return employee.parallelStream().map(i -> {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setId(i.getId());
            employeeDto.setName(i.getName());
            employeeDto.setLogin(i.getLogin());
            employeeDto.setSalary(String.valueOf(i.getSalary()));
            employeeDto.setStartDate(String.valueOf(i.getStartDate()));
            return employeeDto;
        }).collect(Collectors.toList());
    }
}
