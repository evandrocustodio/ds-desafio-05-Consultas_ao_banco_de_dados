package com.devsuperior.movieflix.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.movieflix.entities.MovieDTO;
import com.devsuperior.movieflix.entities.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieResource {

	@Autowired
	private MovieService movieService;

	@GetMapping
	public ResponseEntity<Page<MovieDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
			@RequestParam(value = "genreId", defaultValue = "0" ) Long genreId,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<MovieDTO> movies = movieService.findAllPaged(pageRequest, genreId);

		return ResponseEntity.ok().body(movies);	

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
		MovieDTO dto = movieService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/{id}/reviews")
	public ResponseEntity<List<ReviewDTO>> findByIdWithReviews(@PathVariable Long id) {
		List<ReviewDTO> reviews = movieService.findByIdWithReviews(id);
		return ResponseEntity.ok().body(reviews);
	}

	@PostMapping
	public ResponseEntity<MovieDTO> insert(@Valid @RequestBody MovieDTO dto) {
		MovieDTO returnedDTO = movieService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(returnedDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(returnedDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<MovieDTO> update(@PathVariable Long id, @Valid @RequestBody MovieDTO dto) {
		MovieDTO user = movieService.update(id, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		movieService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
