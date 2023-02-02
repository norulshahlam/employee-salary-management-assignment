package com.shah.employeesalarymanagementassignment.helper;

import com.shah.employeesalarymanagementassignment.exception.EmployeeException;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class EmployeeHelper {

    public static void checkFileEmpty(MultipartFile file) {
        if (ObjectUtils.anyNull(file)) {
            log.error("No file uploaded");
            throw new EmployeeException("No file uploaded");
        } else if (file.isEmpty()) {
            log.error("Empty file");
            throw new EmployeeException("Empty file");
        } else if (!Objects.requireNonNull(file.getContentType()).equals("text/csv")) {
            log.error("Invalid csv type");
            throw new EmployeeException("Invalid csv type");
        }
    }

    public static void findDuplicates(List<EmployeeDto> dto) {
        Map<String, Long> collect = dto.stream().collect(Collectors.groupingBy(EmployeeDto::getId, Collectors.counting())).entrySet().stream().filter(v -> v.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!collect.isEmpty()) {
            log.error("duplicates id found: {}", collect);
            throw new EmployeeException("duplicates id found: " + collect);
        }
    }

}
