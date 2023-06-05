package com.tododiary.tododiarybe.entity;

import java.sql.Date;
import java.util.Collection;

import com.tododiary.tododiarybe.security.AuthProvider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String password;
	
	@Column(nullable = false)
	private String name;

	private String avatarUri;
	
	private String role;
	
	@Enumerated(EnumType.STRING)
    private AuthProvider provider;
	
	private String providerId;
	
	@OneToOne
	@JoinColumn
	private File avatar;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Collection<Todo> listTodo;
}
