package com.centime.service1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NameDTO {
	
	@NotBlank(message = "Name cannot be empty")
    private String name;
	@NotBlank(message = "Surname cannot be empty")
    private String surName;
}
