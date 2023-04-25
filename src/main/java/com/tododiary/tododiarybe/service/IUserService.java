package com.tododiary.tododiarybe.service;

import java.util.Optional;

import com.tododiary.tododiarybe.entity.User;

public interface IUserService {
	
	boolean existsByUsername(String username);
	
	User registerNewUser(User user);
	
	Optional<User> findByUsername(String username);
}
