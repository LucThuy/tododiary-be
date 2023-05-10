package com.tododiary.tododiarybe.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododiary.tododiarybe.dto.UserDto;
import com.tododiary.tododiarybe.entity.User;
import com.tododiary.tododiarybe.service.IUserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/main")
public class MainController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/user")
	public ResponseEntity<UserDto> user(@AuthenticationPrincipal UserDetails curUser){
		User user = userService.findByUsername(curUser.getUsername()).get();
		UserDto userDto = modelMapper.map(user, UserDto.class);
		userDto.setAvatar(user.getAvatar().getUri());
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
}
