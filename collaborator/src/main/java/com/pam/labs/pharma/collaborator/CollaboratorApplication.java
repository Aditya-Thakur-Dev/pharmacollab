package com.pam.labs.pharma.collaborator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CollaboratorApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CollaboratorApplication.class, args);
	}

}
