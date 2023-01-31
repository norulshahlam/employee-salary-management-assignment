package com.shah.employeesalarymanagementassignment.helper;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.shah.employeesalarymanagementassignment.model.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
@Slf4j
public class CsvHelper {

    public static List<EmployeeDto> csvParser(MultipartFile file) throws IOException {
        log.info("inside CsvHelper::csvParser");

        // parse CSV file to create a list of `EmployeeDto` objects
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        // create csv bean reader
        CsvToBean<EmployeeDto> supplementCsvToBean = new CsvToBeanBuilder<EmployeeDto>(reader)
                .withType(EmployeeDto.class)
                .withIgnoreQuotations(true)
                .build();

        // convert `CsvToBean` object to list of EmployeeDto
        List<EmployeeDto> supplementList = supplementCsvToBean.parse();

        reader.close();
        return supplementList;
    }
}
