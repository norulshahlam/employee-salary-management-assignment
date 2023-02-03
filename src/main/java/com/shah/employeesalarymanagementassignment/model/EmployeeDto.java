package com.shah.employeesalarymanagementassignment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    @NotBlank
    private String id; // primary key of the table
    @NotBlank

    private String login; // login value is unique in table
    @NotBlank
    private String name; // non unique full name of the employee
    @NotBlank
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    private String salary; // always >= 0

    // valid date formats are
    // dd-MMM-yy: example -> 16-Nov-01
    // yyyy-MM-dd: example -> 2001-11-16
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "[d-MMM-yy][yyyy-MM-d]")
    @Pattern(regexp = "(([12]\\d{3}-(0[0-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]|\\d))|(^\\d{2}-[a-zA-Z]{3}-[12]\\d$))",  message = "date format invalid")
    private String startDate;
}
