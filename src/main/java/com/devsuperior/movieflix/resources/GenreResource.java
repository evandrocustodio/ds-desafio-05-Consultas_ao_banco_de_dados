package com.devsuperior.movieflix.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.movieflix.entities.GenreDTO;
import com.devsuperior.movieflix.services.GenreService;

@RestController
@RequestMapping(value = "/genres")
public class GenreResource {

	@Autowired
	private GenreService genreService;

	@GetMapping
	public ResponseEntity<List<GenreDTO>> findAll() {

		List<GenreDTO> genres = genreService.findAll();

		return ResponseEntity.ok().body(genres);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<GenreDTO> findById(@PathVariable Long id) {
		GenreDTO dto = genreService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<GenreDTO> insert(@Valid @RequestBody GenreDTO dto) {
		GenreDTO returnedDTO = genreService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(returnedDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(returnedDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<GenreDTO> update(@PathVariable Long id, @Valid @RequestBody GenreDTO dto) {
		GenreDTO user = genreService.update(id, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		genreService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
