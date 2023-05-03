package com.tododiary.tododiarybe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tododiary.tododiarybe.entity.File;

public interface IFileRepository extends JpaRepository<File, String> {
	
	Optional<File> findById(String id);
}
