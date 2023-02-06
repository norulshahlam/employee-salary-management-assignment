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
    private String id;
    @Column(unique = true)
    private String login;
    private String name;
    /**
     * value must be more than 0
     */
    private double salary;

    /**
     * valid date formats are dd-MMM-yy: example -> 16-Nov-01 | yyyy-MM-dd: example -> 2001-11-16
     */
    private LocalDate startDate;

}
