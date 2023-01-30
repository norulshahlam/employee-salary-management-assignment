package com.shah.employeesalarymanagementassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private String startDate;
}
