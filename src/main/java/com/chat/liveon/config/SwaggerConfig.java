package com.chat.liveon.config;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI customApi() {
        return new OpenAPI()
            .info(new io.swagger.v3.oas.models.info.Info()
                    .title("LiveOn API")
                    .version("v1.0.0")
                    .description("LiveOn API"));
    }

    @Bean
    public GroupedOpenApi api() {
        String[] paths = {"/api/**"};
        String[] packagesToScan = {"com.chat.liveon"};
        return GroupedOpenApi.builder()
            .group("springdoc-openapi")
            .pathsToMatch(paths)
            .packagesToScan(packagesToScan)
            .build();
    }
}
