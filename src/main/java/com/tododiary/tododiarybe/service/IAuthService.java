package com.tododiary.tododiarybe.service;

import org.springframework.web.multipart.MultipartFile;

import com.tododiary.tododiarybe.dto.LoginDto;
import com.tododiary.tododiarybe.dto.RegisterDto;

public interface IAuthService {

	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto, MultipartFile avatar);
}
