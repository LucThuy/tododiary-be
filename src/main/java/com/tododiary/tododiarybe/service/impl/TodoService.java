package com.tododiary.tododiarybe.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.time.DateUtils;
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
	public Collection<Todo> getListTodayTodo(String userUsername) {
		Collection<Todo> todayTodo = todoRepository.findByUserUsernameAndDate(userUsername,
				new Date(System.currentTimeMillis()));

		return todayTodo;
	}

	@Override
	public Todo createTodayTodo(String userUsername) {
		Todo todo = Todo.builder().user(userRepository.findByUsername(userUsername).get())
				.date(new Date(System.currentTimeMillis())).posX(0).posY(0).landscape(false).size("medium")
				.title("Today Todo").note("Default note").listTask(new ArrayList<>()).build();

		return todoRepository.save(todo);
	}

	@Override
	public Todo updateTodayTodo(Todo todo) {
		Todo updatedTodo = todoRepository.findById(todo.getId()).get();

		todo.setListTask(updatedTodo.getListTask());
		todo.setUser(updatedTodo.getUser());

		return todoRepository.save(todo);
	}

	@Override
	public Todo getTodayTodo(String userUsername, String id) {
		Todo todo = todoRepository.findById(id).get();

		if (DateUtils.isSameDay(new Date(System.currentTimeMillis()), todo.getDate())
				&& todo.getUser().getUsername().equals(userUsername)) {
			return todo;
		}

		return null;
	}
}
