package com.devsuperior.movieflix.entities;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class UserWithoutReviewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Campo Obrigatório")
	private String name;
	
    @NotEmpty(message = "Campo Obrigatório")
    private String email;

	
	public UserWithoutReviewDTO() {
	}

	public UserWithoutReviewDTO(Long id, String name,  String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public UserWithoutReviewDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
		email =entity.getEmail();
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

}
