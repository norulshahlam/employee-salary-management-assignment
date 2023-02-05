package com.shah.employeesalarymanagementassignment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author NORUL
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    @NotBlank
    private String id;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @NotBlank
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Salary must be in number & greater than 0")
    private String salary;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "[d-MMM-yy][yyyy-MM-d]")
    @Pattern(regexp =
            "(([12]\\d{3}-(0[0-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]|\\d))|(^\\d{2}-[a-zA-Z]{3}-[12]\\d$))",
            message = "invalid date format. Use [dd-MMM-yy] or [yyyy-MM-dd] ")
    private String startDate;
}
