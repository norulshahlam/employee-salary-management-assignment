package com.shah.employeesalarymanagementassignment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDate;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Employee {
    @Id
    private String id; // primary key of the table
    private String login; // login value is unique in table
    private String name; // non unique full name of the employee
    @DecimalMin(value = "0.0", inclusive = false)
    private double salary; // always >= 0

    // valid date formats are
    // dd-MMM-yy: example -> 16-Nov-01
    // yyyy-MM-dd: eample -> 2001-11-16
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "[d-MMM-yy][yyyy-MM-d]")
    private LocalDate startDate;
}
