package com.shah.assignmentemployeesalaryapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author NORUL
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto {
    @NotBlank(message = "id must not be blank")
    @Schema(type = "string", example = "emp00077")
    private String id;
    @NotBlank(message = "login must not be blank")
    @Schema(type = "string", example = "hpoterr")
    private String login;
    @NotBlank(message = "name must not be blank")
    @Schema(type = "string", example = "Harryy Potter")
    private String name;
    @NotBlank(message = "salary must not be blank")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Salary must be in number & greater than 0.0")
    @Schema(type = "string", example = "5500.50")
    private String salary;
    @NotBlank(message = "startDate must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "[d-MMM-yy][yyyy-MM-d]")
    @Pattern(regexp =
            "(([12]\\d{3}-(0[0-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]|\\d))|(^\\d{2}-[a-zA-Z]{3}-[12]\\d$))",
            message = "invalid date format. Use [dd-MMM-yy] or [yyyy-MM-dd] ")
    @Schema(type = "string", example = "2001-11-16")
    private String startDate;
}
