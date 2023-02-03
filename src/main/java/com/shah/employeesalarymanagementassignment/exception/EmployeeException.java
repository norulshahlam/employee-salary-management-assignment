package com.shah.employeesalarymanagementassignment.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeException extends RuntimeException {

    private final String errorMessage;
    private Object data;
}
