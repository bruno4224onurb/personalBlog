package com.generation.personalblog.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI springPersonalBlogOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Personal Blog Project").description("Personal Blog Project - Generation Brazil")
						.version("v0.0.1")
						.license(new License().name("Generation Brazil").url("httops://brazil.generation.org"))
						.contact(new Contact().name("Generation Brazil").url("hhtps://github.com/conteudoGeneration")
								.email("conteudogeneration@generation.org")))
				.externalDocs(new ExternalDocumentation().description("Github")
						.url("https://github.com/conteudoGeneration/"));
	}

	@Bean
	OpenApiCustomizer customizerGlobalHeaderOpenApiCustomizer() {
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Object Persisted!"));
				apiResponses.addApiResponse("204", createApiResponse("Object Excluded!"));
				apiResponses.addApiResponse("400", createApiResponse("Error in the request!"));
				apiResponses.addApiResponse("401", createApiResponse("Access Denied!"));
				apiResponses.addApiResponse("403", createApiResponse("Access Forbidden!"));
				apiResponses.addApiResponse("404", createApiResponse("Object Not Found!"));
				apiResponses.addApiResponse("500", createApiResponse("Error in application!"));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);
	}

}
