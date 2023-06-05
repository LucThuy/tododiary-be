package com.tododiary.tododiarybe.entity;

import java.sql.Date;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Todo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private Date date;
	
	private float posX;
	
	private float posY;
	
	private boolean landscape;
	
	private String title;
	
	private String note;
	
	private String size;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonManagedReference
	private Collection<Task> listTask;

}
