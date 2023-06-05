package com.tododiary.tododiarybe.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tododiary.tododiarybe.dto.LoginDto;
import com.tododiary.tododiarybe.dto.RegisterDto;
import com.tododiary.tododiarybe.entity.File;
import com.tododiary.tododiarybe.entity.User;
import com.tododiary.tododiarybe.exception.ApiException;
import com.tododiary.tododiarybe.repository.IUserRepository;
import com.tododiary.tododiarybe.security.JwtTokenProvider;
import com.tododiary.tododiarybe.service.IAuthService;
import com.tododiary.tododiarybe.service.IFileService;

@Service
public class AuthService implements IAuthService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired 
	private IFileService fileService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDto registerDto, MultipartFile avatar) {

		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "username is already exists");
		}

		User user = modelMapper.map(registerDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");

		User updateUser = userRepository.save(user);
		
		File avatarFile = fileService.save(avatar, "images/" + updateUser.getId());
		updateUser.setAvatar(avatarFile);
		updateUser.setAvatarUri("http://localhost:8080/" + avatarFile.getUri());
		
		userRepository.saveAndFlush(updateUser);
		
		LoginDto loginDto = new LoginDto(registerDto.getUsername(), registerDto.getPassword());
		String token = login(loginDto);

		return token;
	}

}
