package com.tododiary.tododiarybe.dto;

import lombok.Data;

@Data
public class TaskDto {

	private String id;

	private String type;

	private int progress;

	private String detail;
}
