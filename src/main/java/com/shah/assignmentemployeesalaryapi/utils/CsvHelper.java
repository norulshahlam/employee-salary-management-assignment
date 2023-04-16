package com.shah.assignmentemployeesalaryapi.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.shah.assignmentemployeesalaryapi.model.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * @author NORUL
 */
@Slf4j
public class CsvHelper {

    public static List<EmployeeDto> csvParser(MultipartFile file) throws IOException {
        log.info("inside CsvHelper::csvParser");

        // parse CSV file to create a list of `EmployeeDto` objects
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        // create csv bean reader
        List<EmployeeDto> dto = new CsvToBeanBuilder<EmployeeDto>(reader)
                .withType(EmployeeDto.class)
                .withIgnoreQuotations(true)
                .withThrowExceptions(true)
                .build()
                .parse();
        reader.close();

        return dto;
    }
}
