package com.tododiary.tododiarybe.dto;

import java.sql.Date;
import java.util.List;

import com.tododiary.tododiarybe.entity.Task;

import lombok.Data;

@Data
public class TodoDto {
	
	private String id;
	
	private Date date;
	
	private float poxX;
	
	private float poxY;
	
	private boolean landscape;
	
	private String size;
	
	private List<Task> listTask;
}
