package com.tododiary.tododiarybe.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododiary.tododiarybe.dto.TodoDto;
import com.tododiary.tododiarybe.dto.TodoResponse;
import com.tododiary.tododiarybe.entity.Task;
import com.tododiary.tododiarybe.entity.Todo;
import com.tododiary.tododiarybe.service.ITaskService;
import com.tododiary.tododiarybe.service.ITodoService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/todaytodo")
public class TodayTodoController {

	@Autowired
	private ITodoService todoService;

	@Autowired
	private ITaskService taskService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("")
	public ResponseEntity<TodoResponse> getTodayTodo(@AuthenticationPrincipal UserDetails user) {
		Collection<Todo> todayTodo = todoService.getTodayTodo(user.getUsername());
		List<TodoDto> listTodo = todayTodo.stream().map(todo -> modelMapper.map(todo, TodoDto.class))
				.collect(Collectors.toList());

		TodoResponse todoResponse = new TodoResponse(listTodo);

		return new ResponseEntity<>(todoResponse, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Todo> createTodayTodo(@AuthenticationPrincipal UserDetails user,
			@RequestBody TodoDto todoDto) {
		Todo todo = modelMapper.map(todoDto, Todo.class);

		Todo todayTodo = todoService.createTodayTodo(user.getUsername(), todo);
		List<Task> listTask = todoDto.getListTask().stream().map(task -> taskService.createTask(todayTodo, task))
				.collect(Collectors.toList());
		todayTodo.setListTask(listTask);

		return new ResponseEntity<>(todayTodo, HttpStatus.OK);
	}
}
