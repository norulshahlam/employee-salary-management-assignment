package com.shah.assignmentemployeesalaryapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author NORUL
 */
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
    private double salary;
    private LocalDate startDate;
}