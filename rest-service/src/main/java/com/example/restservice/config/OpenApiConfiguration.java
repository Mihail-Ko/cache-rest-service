package com.example.restservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI microserviceOpenAPI() {
        Server server = new Server();
        server.setUrl("");
        return new OpenAPI()
            .servers(List.of(server))
            .info(
                new Info()
                    .title("Api для учёта книг")
                    .description("Сервис кеширования для горизонтально масштабируемого HTTP-сервиса")
                    .version("1.0")
            );
    }
}