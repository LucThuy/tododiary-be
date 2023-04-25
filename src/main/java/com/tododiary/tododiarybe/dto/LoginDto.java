package com.tododiary.tododiarybe.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

	@NotBlank
	private String username;
	
	@NotBlank
	@Length(min= 8)
	private String password;
}
