package com.devsuperior.movieflix.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Campo Obrigatório")
	private String name;
	
	
	@Email(message = "Informe um email válido")
	private String email;

	private Set<RoleDTO> roles = new HashSet<>();
	
	private Set<ReviewDTO> reviews = new HashSet<>(); 

	public UserDTO() {
	}

	public UserDTO(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public UserDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		entity.getRoles().forEach( r -> this.getRoles().add(new RoleDTO(r)));
		entity.getReviews().forEach(r -> this.getReviews().add(new ReviewDTO(r)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}
	
	public Set<ReviewDTO> getReviews() {
		return reviews;
	}

}
