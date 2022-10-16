package com.devsuperior.movieflix.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT obj FROM Movie obj WHERE obj.genre.id = :genreId")
	Page<Movie> findAllByGenre(Pageable pageable, Long genreId);
	

	@Query("SELECT m FROM Movie m "
			+ "JOIN FETCH m.reviews r "
			+ "WHERE m.id = :movieId")
	Optional<Movie> getMovieDetalhe(Long movieId);

}
	