package com.tododiary.tododiarybe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tododiary.tododiarybe.dto.JWTAuthResponse;
import com.tododiary.tododiarybe.dto.LoginDto;
import com.tododiary.tododiarybe.dto.RegisterDto;
import com.tododiary.tododiarybe.service.IAuthService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private IAuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> login(@RequestBody @Valid LoginDto loginDto){
		String token = authService.login(loginDto);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
	@PostMapping("/register")
	public ResponseEntity<JWTAuthResponse> register(@ModelAttribute @Valid RegisterDto registerDto, @RequestParam("avatar") MultipartFile avatar) {
		String token = authService.register(registerDto, avatar);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}

}
