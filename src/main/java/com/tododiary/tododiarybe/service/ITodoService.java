package com.tododiary.tododiarybe.service;

import java.util.Collection;

import com.tododiary.tododiarybe.entity.Todo;

public interface ITodoService {

	Collection<Todo> getTodayTodo(String userUsername);

	Todo createTodayTodo(String userUsername, Todo todo);

}
