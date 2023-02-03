package com.shah.employeesalarymanagementassignment.helper;

import com.opencsv.bean.CsvToBeanBuilder;
import com.shah.employeesalarymanagementassignment.exception.EmployeeException;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class CsvHelper {

    public static List<EmployeeDto> csvParser(MultipartFile file) throws IOException {
        log.info("inside CsvHelper::csvParser");

        // parse CSV file to create a list of `EmployeeDto` objects
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        // create csv bean reader
        List<EmployeeDto> beans = new CsvToBeanBuilder<EmployeeDto>(reader)
                .withType(EmployeeDto.class)
                .withIgnoreQuotations(true)
                .withThrowExceptions(true)
                .build()
                .parse();
        reader.close();

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

        return beans;
    }
}
