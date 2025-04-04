package com.bootcamp.bancodigital.neonautas.neofundz.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class OpenApiConfig {
	
	@Value("${api.title}")
	private String apiTitle;

	@Value("${api.version}")
	private String apiVersion;

	@Value("${api.description}")
	private String apiDescription;
	
	@Value("${server.servlet.context-path}")
	private String server_context_path;
	
	@Value("${server.port}")
	private String server_port;
	
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(apiTitle)
                        .version(apiVersion)
                        .description(apiDescription))
                .servers(List.of(
                        new Server().url("http://localhost:"+server_port+server_context_path).description(apiDescription)
                ));
    }
}
