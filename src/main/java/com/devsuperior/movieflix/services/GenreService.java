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
import com.devsuperior.movieflix.entities.GenreDTO;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;;

@Service
public class GenreService {

	@Autowired
	private GenreRepository genreRepository;

	public GenreService() {
	}

	@Transactional(readOnly = true)
	public List<GenreDTO> findAll() {
		List<Genre> genres = genreRepository.findAll();
		return genres.stream().map(c -> new GenreDTO(c)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public GenreDTO findById(Long id) {
		Optional<Genre> object = genreRepository.findById(id);
		Genre entity = object.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
		return new GenreDTO(entity);
	}

	@Transactional
	public GenreDTO insert(GenreDTO dto) {
		Genre entity = new Genre();
		entity.setName(dto.getName());
		entity = genreRepository.save(entity);
		return new GenreDTO(entity);
	}

	@Transactional
	public GenreDTO update(Long id, GenreDTO dto) {
		Genre entity;
		try {
			entity = genreRepository.findById(id).get();
			entity.setName(dto.getName());
			entity = genreRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id Not Found: " + id);
		}
		return new GenreDTO(entity);
	}

	public void delete(Long id) {
		try {
			genreRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id Not Found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}
	}

}
