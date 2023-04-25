package com.tododiary.tododiarybe.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tododiary.tododiarybe.entity.User;
import com.tododiary.tododiarybe.repository.IUserRepository;
import com.tododiary.tododiarybe.service.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public User registerNewUser(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@Override
	public Optional<User> findByUsername(String username){
		return userRepository.findByUsername(username);
	}
}
