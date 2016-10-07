package com.petstore.pet;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableDiscoveryClient
@EnableHystrix
@SpringBootApplication
@EnableSwagger2
public class PetApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	public Docket swaggerApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("PetStore")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/pet.*"))
				.build();
	}


	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("Pet Store API")
				.description("A microservice based PetStore")
				.contact("Ali Bhagat")
				.version("1.0")
				.build();
	}
}
