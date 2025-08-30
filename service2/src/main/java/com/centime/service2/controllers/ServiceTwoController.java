package com.centime.service2.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ServiceTwoController {

	@GetMapping(value = "/hello", produces = "application/json")
	public ResponseEntity<String> serviceOne() {
		log.info("Greeting hello API called!!!");
		return ResponseEntity.ok("Hello");
    }
	
}
