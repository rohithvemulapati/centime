package com.centime.service3.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centime.service3.dto.NameDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ServiceThreeController {

	@PostMapping(value = "/concat-names", produces = "application/json")
	public ResponseEntity<String> concatNames(@RequestBody @Valid NameDTO nameDto) {

		log.info("Concatenated names: {} {}", nameDto.getName(), nameDto.getSurName());
		return ResponseEntity.ok(nameDto.getName() + " " + nameDto.getSurName());
    }
	
}
