package com.shah.employeesalarymanagementassignment.advice;


import com.shah.employeesalarymanagementassignment.exception.EmployeeException;
import com.shah.employeesalarymanagementassignment.model.EmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static com.shah.employeesalarymanagementassignment.model.ResponseStatus.FAILURE;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EmployeeException.class})
    @ResponseBody
    public EmployeeResponse handleSupplementException(EmployeeException e) {
        return EmployeeResponse.builder()
                .status(FAILURE)
                .data(e.getData())
                .message(e.getErrorMessage())
                .build();
    }
    /**
     * When an action violates a constraint on repository structure
     *
     * @param req
     * @param e
     * @return
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public EmployeeResponse handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException e) {

        List<String> cause = e.getConstraintViolations().stream().map(violation -> violation.getPropertyPath().toString() + ": " + violation.getMessage()).collect(Collectors.toList());
        log.error("requestUrl : {}, occurred an error : {}, exception detail : {}", req.getRequestURI(), cause, e);
        String collect = String.join(", ", cause);
        return EmployeeResponse.builder()
                .status(FAILURE)
                .data(collect)
                .build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public EmployeeResponse handleOtherException(Exception e) {
        return EmployeeResponse.builder()
                .status(FAILURE)
                .message(e.getLocalizedMessage())
                .build();
    }

}
