package com.shah.assignmentemployeesalaryapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NORUL
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(OpenApiProperties properties) {
        return new OpenAPI()
                .info(getInfo(properties));
    }
    private Info getInfo(OpenApiProperties properties) {
        return new Info()
                .title(properties.getProjectTitle())
                .description(properties.getProjectDescription())
                .version(properties.getProjectVersion())
                .license(getLicense());
    }

    private License getLicense() {
        return new License()
                .name("Unlicensed")
                .url("https://unlicense.org/");
    }
}
