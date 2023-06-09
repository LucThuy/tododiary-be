package com.tododiary.tododiarybe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tododiary.tododiarybe.entity.User;


public interface IUserRepository extends JpaRepository<User, String> {

	boolean existsByUsername(String username);
	
	Optional<User> findByUsername(String username);
}
