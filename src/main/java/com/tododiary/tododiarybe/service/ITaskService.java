package com.tododiary.tododiarybe.service;

import com.tododiary.tododiarybe.entity.Task;
import com.tododiary.tododiarybe.entity.Todo;

public interface ITaskService {

	Task createTask(Todo todo, Task task);

}
