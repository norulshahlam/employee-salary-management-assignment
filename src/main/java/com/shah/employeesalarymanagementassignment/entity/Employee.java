package com.shah.employeesalarymanagementassignment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @Column(unique = true)
    private String login; // login value is unique in table
    private String name; // non unique full name of the employee
    private double salary; // always >= 0

    // valid date formats are
    // dd-MMM-yy: example -> 16-Nov-01
    // yyyy-MM-dd: eample -> 2001-11-16
    private LocalDate startDate;
}
