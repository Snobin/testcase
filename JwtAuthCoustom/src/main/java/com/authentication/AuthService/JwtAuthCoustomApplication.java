package com.authentication.AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SpringBootApplication
public class JwtAuthCoustomApplication {
	
	@Bean
	public OpenAPI openApi() {
		Info info=new Info().description("Full stack").title("abcd").version("V1");
		return new OpenAPI().info(info);
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthCoustomApplication.class, args);
	}

}
