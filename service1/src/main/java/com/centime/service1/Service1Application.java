package com.centime.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "com.centime.service1",        // your main service packages
	    "com.centime.common_service"           // the common module packages
	})
public class Service1Application {

	public static void main(String[] args) {
		SpringApplication.run(Service1Application.class, args);
	}

	@Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build(); // Sleuth interceptors are added here
    }
}
