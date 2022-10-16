package com.devsuperior.movieflix.entities;

import java.io.Serializable;
import java.util.Objects;

import com.devsuperior.movieflix.services.validations.ReviewInsertValid;

@ReviewInsertValid
public class ReviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String text;    

    private Long movieId;

    private UserWithoutReviewDTO user;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String text, Long movieId) {
        super();
        this.id = id;
        this.text = text;
        this.movieId = movieId;
    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.text = review.getText();
        this.movieId = review.getMovie().getId();
        this.user = new UserWithoutReviewDTO(review.getUser());
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
        ReviewDTO other = (ReviewDTO) obj;
        return Objects.equals(id, other.id);
    }

    public UserWithoutReviewDTO getUser() {
        return user;
    }

    public void setUser(UserWithoutReviewDTO user) {
        this.user = user;
    }

}
