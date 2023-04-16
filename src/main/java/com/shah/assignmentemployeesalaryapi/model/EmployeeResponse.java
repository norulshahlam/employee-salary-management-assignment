package com.shah.assignmentemployeesalaryapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * @author NORUL
 */
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

    public static <T> EmployeeResponse<T> SuccessResponse(T data) {
        return new EmployeeResponse(ResponseStatus.SUCCESS, data,null);
    }
    public static EmployeeResponse SuccessResponse(String message) {
        return EmployeeResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .message(message)
                .build();
    }
}
