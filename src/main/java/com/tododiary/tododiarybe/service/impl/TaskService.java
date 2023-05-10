package com.tododiary.tododiarybe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tododiary.tododiarybe.entity.Task;
import com.tododiary.tododiarybe.entity.Todo;
import com.tododiary.tododiarybe.repository.ITaskRepository;
import com.tododiary.tododiarybe.service.ITaskService;

@Service
public class TaskService implements ITaskService {

	@Autowired
	private ITaskRepository taskRepository;
	
	@Override
	public Task createTask(Todo todo, Task task) {
		task.setTodo(todo);
		
		return taskRepository.save(task);
	}
}
