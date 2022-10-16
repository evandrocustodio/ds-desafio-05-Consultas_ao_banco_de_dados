package com.devsuperior.movieflix.entities;

import java.io.Serializable;
import java.util.Objects;

public class ReviewInsertDTO implements Serializable {	

	private static final long serialVersionUID = 1L;

	private Long id;

	private String text;

	private Long movieId;

	private Long userId;

	public ReviewInsertDTO() {
	}

	public ReviewInsertDTO(Long id, String text, Long movieId) {
		super();
		this.id = id;
		this.text = text;
		this.movieId = movieId;
	}

	public ReviewInsertDTO(Review review) {
		this.id = review.getId();
		this.text = review.getText();
		this.movieId = review.getMovie().getId();
		this.userId = review.getUser().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewInsertDTO other = (ReviewInsertDTO) obj;
		return Objects.equals(id, other.id);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
