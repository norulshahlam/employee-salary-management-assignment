package com.shah.employeesalarymanagementassignment.helper;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.exception.EmployeeException;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class EmployeeHelper {

    public static void checkFileEmpty(MultipartFile file) {
        log.info("inside checkFileEmpty");
        if (ObjectUtils.anyNull(file)) {
            log.error("No file uploaded");
            throw new EmployeeException("No file uploaded", null);
        } else if (file.isEmpty()) {
            log.error("Empty file");
            throw new EmployeeException("Empty file", null);
        } else if (!Objects.requireNonNull(file.getContentType()).equals("text/csv")) {
            log.error("Invalid csv type");
            throw new EmployeeException("Invalid csv type", null);
        }
    }

    public static void findDuplicates(List<EmployeeDto> dto) {
        log.info("inside findDuplicates");
        Map<String, Long> collect = dto.stream().collect(Collectors.groupingBy(EmployeeDto::getId, Collectors.counting())).entrySet().stream().filter(v -> v.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!collect.isEmpty()) {
            log.error("duplicates id found: {}", collect);
            throw new EmployeeException("duplicates id found: " + collect, null);
        }
    }

    public static List<EmployeeDto> ignoreRows(List<EmployeeDto> dto) {
        log.info("inside ignoreRows");
         return dto.stream().filter(i -> !i.getId().contains("#")).collect(Collectors.toList());
    }

    public static LocalDate dateConverter(EmployeeDto i) throws ParseException {

        String date = i.getStartDate();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd-MMM-yy");

        if (GenericValidator.isDate(date, "yyyy-MM-dd", true))
            return LocalDate.parse(date, format1);
        if (GenericValidator.isDate(date, "dd-MMM-yy", true))
            return LocalDate.parse(date, format2);
        throw new EmployeeException("Invalid Date format for id " + i.getId(), i.getStartDate());
    }

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

    public static void employeeValidator(List<EmployeeDto> beans) {
        Validator factory = Validation.buildDefaultValidatorFactory().getValidator();
        List<String> data = new ArrayList<>();

        for (EmployeeDto dto : beans) {
            List<String> errorMessage = new ArrayList<>();
            Set<ConstraintViolation<EmployeeDto>> violations = factory.validate(dto);
            for (ConstraintViolation<EmployeeDto> violation : violations) {
                if (StringUtils.isNotEmpty(violation.getMessage())) {
                    log.info(dto.getId() + " - " + violation.getMessage());
                    errorMessage.add(violation.getMessage());
                }
            }
            if (!errorMessage.isEmpty()) {
                data.add("id: " + dto.getId() + " - " + String.join(", ", errorMessage));
            }
        }
        if (!data.isEmpty()) {
            log.error(data.toString());
            throw new EmployeeException("item validation failed", data);
        }
    }
}