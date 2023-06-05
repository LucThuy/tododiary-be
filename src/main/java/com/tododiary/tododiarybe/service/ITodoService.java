package com.tododiary.tododiarybe.service;

import java.util.Collection;

import com.tododiary.tododiarybe.entity.Todo;

public interface ITodoService {

	Collection<Todo> getListTodayTodo(String userUsername);

	Todo createTodayTodo(String userUsername);

	Todo updateTodayTodo(Todo todo);

	Todo getTodayTodo(String userUsername, String id);

	

}
