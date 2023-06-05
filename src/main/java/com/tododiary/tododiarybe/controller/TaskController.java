package com.tododiary.tododiarybe.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tododiary.tododiarybe.dto.TaskDto;
import com.tododiary.tododiarybe.entity.Task;
import com.tododiary.tododiarybe.entity.Todo;
import com.tododiary.tododiarybe.service.ITaskService;
import com.tododiary.tododiarybe.service.ITodoService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/task")
public class TaskController {

	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private ITodoService todoService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/{todoId}")
	public ResponseEntity<TaskDto> createTask(@AuthenticationPrincipal UserDetails user,
			@PathVariable("todoId") String todoId) {
		Todo todo = todoService.getTodayTodo(user.getUsername(), todoId);	
		
		Task task = taskService.createTask(todo);
		
		TaskDto taskDto = modelMapper.map(task, TaskDto.class);
		
		return new ResponseEntity<>(taskDto, HttpStatus.OK);
	}
	
	@PutMapping("")
	public ResponseEntity<TaskDto> updateTask(@AuthenticationPrincipal UserDetails user, @RequestBody TaskDto taskDto){
		Task task = taskService.updateTask(modelMapper.map(taskDto, Task.class));
		
		TaskDto taskResDto = modelMapper.map(task, TaskDto.class);
		
		return new ResponseEntity<>(taskResDto, HttpStatus.OK);
	}

}
