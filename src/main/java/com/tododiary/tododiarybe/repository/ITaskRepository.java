package com.tododiary.tododiarybe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tododiary.tododiarybe.entity.Task;

public interface ITaskRepository extends JpaRepository<Task, String> {

}
