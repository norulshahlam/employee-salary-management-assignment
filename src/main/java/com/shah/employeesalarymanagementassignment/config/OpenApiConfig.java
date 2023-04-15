package com.shah.assignmentschooladminapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NORUL
 */
@Configuration
@SecurityScheme(
        name = "basicAuth", // can be set to anything
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "School Admin API",
                version = "v1",
                description = "Management of student and teacher data")
)
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
