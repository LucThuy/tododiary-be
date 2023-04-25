package com.tododiary.tododiarybe.dto;

import lombok.Data;

@Data
public class JWTAuthResponse {

	private String accessToken;
	private String tokenType = "Bearer";
}
