package com.tododiary.tododiarybe.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tododiary.tododiarybe.entity.Todo;
import com.tododiary.tododiarybe.repository.ITodoRepository;
import com.tododiary.tododiarybe.repository.IUserRepository;
import com.tododiary.tododiarybe.service.ITodoService;

@Service
public class TodoService implements ITodoService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private ITodoRepository todoRepository;

	@Override
	public Collection<Todo> getTodayTodo(String userUsername) {
		Collection<Todo> todayTodo = todoRepository.findByUserUsernameAndDate(userUsername,
				new Date(System.currentTimeMillis()));
		
		return todayTodo;
	}
	
	@Override
	public Todo createTodayTodo(String userUsername, Todo todo) {
		todo.setDate(new Date(System.currentTimeMillis()));
		todo.setUser(userRepository.findByUsername(userUsername).get());
		
		return todoRepository.save(todo);
	}
}
