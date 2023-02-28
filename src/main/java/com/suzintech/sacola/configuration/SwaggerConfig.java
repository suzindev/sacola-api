package com.suzintech.sacola.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Sacola API")
                        .description("Sacola API para Servir uma Aplicação de Delivery")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api-docs")
                .pathsToMatch("/sacola/**")
                .build();
    }
}
