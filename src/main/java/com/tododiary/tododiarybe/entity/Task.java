package com.tododiary.tododiarybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String type;
	
	private int progress;
	
	private String detail;
	
	@ManyToOne
	@JoinColumn(name = "todo_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonBackReference
	private Todo todo;
}
