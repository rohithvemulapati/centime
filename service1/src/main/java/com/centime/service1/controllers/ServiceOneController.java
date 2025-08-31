package com.centime.service1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.centime.service1.dto.NameDTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ServiceOneController {

	@Autowired
	public WebClient webClient;
	
	@Value("${service2.url}")
	public String service2Url;
	@Value("${service3.url}")
	public String service3Url;
	
    @Operation(summary = "Check if Service 1 is up", description = "Returns 'Up' if the service is running")
	@GetMapping(value = "/running-status", produces = "application/json")
	public ResponseEntity<String> serviceOne() {
		log.info("Service 1 is running...");
		return ResponseEntity.ok("Up");
	}
	
    @Operation(summary = "Greet user", description = "Greets the user by calling Service 2 and Service 3")
	@PostMapping(value = "/greet-user", produces = "application/json")
	public ResponseEntity<String> greetUser(@RequestBody @Valid NameDTO nameDto) {
        log.info("Received request to greet user: {} {}", nameDto.getName(), nameDto.getSurName());
        
        try {
            log.info("Received request to greet user: {} {}", nameDto.getName(), nameDto.getSurName());
            String greeting = callService2();
            log.info("Received greeting from Service 2: {}", greeting);
            String fullName = callService3(nameDto);
            log.info("Received full name from Service 3: {}", fullName);

            return ResponseEntity.ok(greeting + ", " + fullName + "!");
        } 
		catch (Exception e) {
			log.error("Error while greeting user: {}", e.getMessage());
			return ResponseEntity.status(500).body("Error while processing the request");
		}	
	}

	public String callService2() {
		log.info("Calling service 2!!!, url : {}", service2Url + "/hello");
		return webClient.get().uri(service2Url + "/hello").retrieve().bodyToMono(String.class).block(); // blocking
	}

	public String callService3(NameDTO request) {
		log.info("Caling service 3!!!, url : {}", service3Url + "/concat-names");
		return webClient.post().uri(service3Url + "/concat-names").bodyValue(request).retrieve()
				.bodyToMono(String.class).block();
	}

}
