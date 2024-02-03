package com.interland.testcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication

public class TestcaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestcaseApplication.class, args);
	}

	@Bean
	public OpenAPI openApi() {
		Info info = new Info().description("Online Exam Application with Springboot and Angular").title("Testcase")
				.version("V1");
		return new OpenAPI().info(info);
	}

}
