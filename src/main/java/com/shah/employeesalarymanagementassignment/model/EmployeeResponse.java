package com.shah.employeesalarymanagementassignment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse<T> {

    @NonNull
    ResponseStatus status;
    T data;
    String message;

    public static EmployeeResponse SuccessResponse(Object data) {
        return EmployeeResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(data)
                .build();
    }
    public static EmployeeResponse SuccessResponse(String message) {
        return EmployeeResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .message(message)
                .build();
    }
}
