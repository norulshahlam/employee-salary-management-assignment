package com.shah.assignmentemployeesalaryapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author NORUL
 */
@Data
@AllArgsConstructor
public class EmployeeException extends RuntimeException {

    private final String errorMessage;
    private Object data;
}
