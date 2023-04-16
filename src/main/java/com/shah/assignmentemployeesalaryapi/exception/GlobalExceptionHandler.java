package com.shah.assignmentemployeesalaryapi.exception;


import com.shah.assignmentemployeesalaryapi.model.EmployeeResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static com.shah.assignmentemployeesalaryapi.model.ResponseStatus.FAILURE;

/**
 * @author NORUL
 */
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

        List<String> errorMessages = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        log.error("requestUrl : {}, occurred an error : {}, exception detail : {}", req.getRequestURI(), errorMessages, e);
        String collect = String.join(", ", errorMessages);
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
