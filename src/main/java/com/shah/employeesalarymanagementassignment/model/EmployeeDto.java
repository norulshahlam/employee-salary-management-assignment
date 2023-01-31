package com.shah.employeesalarymanagementassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Pattern;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    private String id; // primary key of the table
    private String login; // login value is unique in table
    private String name; // non unique full name of the employee
    private double salary; // always >= 0

    // valid date formats are
    // dd-MMM-yy: example -> 16-Nov-01
    // yyyy-MM-dd: example -> 2001-11-16
    @Pattern(regexp = "/[0-9]{2}-*[a-zA-Z]{3,}-*[0-9]{2}||([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))/")
    private String startDate;
}
