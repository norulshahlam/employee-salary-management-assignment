package com.shah.employeesalarymanagementassignment.util;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class EmployeeMapper {

    public static Employee mapEmployee(List<EmployeeDto> dto) {
        Map<String, Long> collect = dto.stream().collect(Collectors.groupingBy(EmployeeDto::getLogin, Collectors.counting())).entrySet().stream().filter(v -> v.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        log.info("duplicates found: {}", collect);
        return null;
    }
}
