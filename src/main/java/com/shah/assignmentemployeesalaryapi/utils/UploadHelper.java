package com.shah.assignmentemployeesalaryapi.utils;

import com.shah.assignmentemployeesalaryapi.entity.Employee;
import com.shah.assignmentemployeesalaryapi.exception.EmployeeException;
import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import com.shah.assignmentemployeesalaryapi.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author NORUL
 */
@Slf4j
@Service
@AllArgsConstructor
public class UploadHelper {
    public static final String TEXT_CSV = "text/csv";

    private EmployeeRepository employeeRepository;

    public static void checkFileEmpty(MultipartFile file) {
        log.info("inside checkFileEmpty");
        if (ObjectUtils.anyNull(file)) {
            log.error("No file uploaded");
            throw new EmployeeException("No file uploaded", null);
        } else if (file.isEmpty()) {
            log.error("Empty file");
            throw new EmployeeException("Empty file", null);
        } else if (!Objects.requireNonNull(file.getContentType()).equals(TEXT_CSV)) {
            log.error("Invalid csv type");
            throw new EmployeeException("Invalid csv type", null);
        }
    }

    public static void employeeValidator(List<EmployeeDto> dtoList) {
        log.info("inside employeeValidator");

        Validator factory = Validation.buildDefaultValidatorFactory().getValidator();
        List<String> data = new ArrayList<>();

        dtoList.parallelStream().forEach(dto -> {
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
        });
        if (!data.isEmpty()) {
            log.error(data.toString());
            throw new EmployeeException("validation failed", data);
        }
    }

    public static void findDuplicateId(List<EmployeeDto> dto) {
        log.info("inside findDuplicateId");
        Map<String, Long> collect = dto.parallelStream().collect(Collectors.groupingBy(EmployeeDto::getId, Collectors.counting())).entrySet().stream().filter(v -> v.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));



        if (!collect.isEmpty()) {
            log.error("duplicates id found: {}", collect);
            throw new EmployeeException("duplicates id found: " + collect, null);
        }
    }

    public static void findDuplicateLogin(List<EmployeeDto> dto) {
        log.info("inside findDuplicateLogin");
        Map<String, Long> collect = dto.parallelStream().collect(Collectors.groupingBy(EmployeeDto::getLogin, Collectors.counting())).entrySet().stream().filter(v -> v.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!collect.isEmpty()) {
            log.error("duplicates login found: {}", collect);
            throw new EmployeeException("duplicates login found: " + collect, null);
        }
    }

    public static void ignoreRows(List<EmployeeDto> dto) {
        log.info("inside ignoreRows");
        dto.removeIf(i -> i.getId().startsWith("#"));
    }

    public void findDuplicateLoginInDb(List<EmployeeDto> dto) {

        log.info("inside findDuplicateLoginInDb");

        dto.parallelStream().forEach(i -> {
            Employee byLogin = employeeRepository.findDistinctByLogin(i.getLogin());
            if (ObjectUtils.isNotEmpty(byLogin)) {
                if (!i.getId().equalsIgnoreCase(ObjectUtils.requireNonEmpty(byLogin.getId()))) {
                    throw new EmployeeException("login exists in db: " + i.getLogin(), null);
                }
            }
        });
    }
}