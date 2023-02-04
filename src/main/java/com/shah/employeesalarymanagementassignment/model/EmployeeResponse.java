package com.shah.employeesalarymanagementassignment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shah.employeesalarymanagementassignment.entity.Employee;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse<T> {

    @NonNull
    ResponseStatus status;
    T data;
    String errorMessage;

    public static EmployeeResponse SuccessResponse(List<Employee> data) {
        return EmployeeResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(data)
                .build();
    }
}
