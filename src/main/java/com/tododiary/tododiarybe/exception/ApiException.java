package com.tododiary.tododiarybe.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
	
	private HttpStatus status;
	private String message;

	public ApiException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public ApiException(String messageSuper, HttpStatus status, String message) {
		super(messageSuper);
		this.status = status;
		this.message = message;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
