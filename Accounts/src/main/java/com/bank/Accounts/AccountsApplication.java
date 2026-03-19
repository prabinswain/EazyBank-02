package com.bank.Accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// to implement Documentation in swagger using @OpenAPIDefinition
@OpenAPIDefinition(
		info = @Info(
				title = "Account Microservice REST API Documentation",
				description = "EazyBank Accounts microservice REST API Documentation,",
				version = "V1",
				contact = @Contact(
						name = "Prabin Swain",
						email = "prabinswain027@gmail.com",
						url = "http://google.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://google.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EazyBank Accounts microservice REST API Documentation. ",
				url = "http://google.com"
		)
)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // to implement auditing propose and to take the user from client
@SpringBootApplication

public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
