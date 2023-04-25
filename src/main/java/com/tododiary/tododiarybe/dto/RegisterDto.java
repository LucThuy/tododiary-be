package com.tododiary.tododiarybe.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {

	@NotBlank
	private String username;

	@NotBlank
	@Length(min= 8)
	private String password;
	
	@NotBlank
	private String name;

}
