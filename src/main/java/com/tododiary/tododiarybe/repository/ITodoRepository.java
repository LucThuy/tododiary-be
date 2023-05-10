package com.tododiary.tododiarybe.repository;

import java.sql.Date;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;


import com.tododiary.tododiarybe.entity.Todo;

public interface ITodoRepository extends JpaRepository<Todo, String> {

	Collection<Todo> findByUserUsernameAndDate(String userUsername, Date date);
}
