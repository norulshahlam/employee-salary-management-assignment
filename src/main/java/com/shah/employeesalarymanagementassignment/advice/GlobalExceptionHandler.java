package com.shah.employeesalarymanagementassignment.advice;


import com.shah.employeesalarymanagementassignment.exception.EmployeeException;
import com.shah.employeesalarymanagementassignment.model.EmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.shah.employeesalarymanagementassignment.model.ResponseStatus.FAILURE;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({EmployeeException.class})
    @ResponseBody
    public EmployeeResponse handleSupplementException(EmployeeException e) {
        return EmployeeResponse.builder()
                .status(FAILURE)
                .data(e.getData())
                .errorMessage(e.getErrorMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public EmployeeResponse handleOtherException(Exception e) {
        return EmployeeResponse.builder()
                .status(FAILURE)
                .errorMessage(e.getMessage())
                .build();
    }

}
