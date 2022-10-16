package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.MovieDTO;
import com.devsuperior.movieflix.entities.ReviewDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private GenreRepository genreRepository;

	public MovieService() {
	}

	@Transactional(readOnly = true)
	public List<MovieDTO> findAll() {
		List<Movie> movies = movieRepository.findAll();
		return movies.stream().map(c -> new MovieDTO(c)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<MovieDTO> findAllPaged(Pageable pageable, Long genreId) {
		Page<Movie> movies = null;

		if (genreId == 0L) {
			movies = movieRepository.findAll(pageable);
		} else {
			movies = movieRepository.findAllByGenre(pageable, genreId);
		}

		return movies.map(c -> new MovieDTO(c));
	}

	@Transactional(readOnly = true)
	public MovieDTO findById(Long id) {
		Optional<Movie> object = movieRepository.getMovieDetalhe(id);
		Movie entity = object.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));		
		return new MovieDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<ReviewDTO> findByIdWithReviews(Long id) {
		Optional<Movie> object = movieRepository.findById(id);
		Movie entity = object.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
		List<ReviewDTO> reviews = entity.getReviews().stream().map(r -> new ReviewDTO(r)).collect(Collectors.toList());
		return reviews;
	}

	@Transactional
	public MovieDTO insert(MovieDTO dto) {
		Movie entity = new Movie();
		entity.setTitle(dto.getTitle());
		entity.setSubTitle(dto.getSubTitle());
		entity.setYear(dto.getYear());
		entity.setImgUrl(dto.getTitle());
		entity.setSynopsis(dto.getSynopsis());
		Optional<Genre> genre = genreRepository.findById(dto.getGenre().getId());
		entity.setGenre(genre.get());
		entity = movieRepository.save(entity);
		return new MovieDTO(entity);
	}

	@Transactional
	public MovieDTO update(Long id, MovieDTO dto) {
		Movie entity;
		try {
			entity = movieRepository.findById(id).get();
			entity.setTitle(dto.getTitle());
			entity.setSubTitle(dto.getSubTitle());
			entity.setYear(dto.getYear());
			entity.setImgUrl(dto.getTitle());
			entity.setSynopsis(dto.getSynopsis());
			Optional<Genre> genre = genreRepository.findById(dto.getGenre().getId());
			entity.setGenre(genre.get());
			entity = movieRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id Not Found: " + id);
		}
		return new MovieDTO(entity);
	}

	public void delete(Long id) {
		try {
			movieRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id Not Found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}
	}

}
