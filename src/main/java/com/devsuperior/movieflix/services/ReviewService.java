package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.ReviewDTO;
import com.devsuperior.movieflix.entities.ReviewInsertDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.entities.UserDTO;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;;

@Service
public class ReviewService {

    private static Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    
    @Autowired  
    private UserService userService;

    public ReviewService() {
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(c -> new ReviewDTO(c)).collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        Review entity = new Review();
        copyDTOToEntity(dto, entity);
        entity = reviewRepository.save(entity);
        return new ReviewDTO(entity);
    }

    public void delete(Long id) {
        try {
            reviewRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id Not Found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }
    }

    private void copyDTOToEntity(ReviewDTO dto, Review entity) {
        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
        entity.setMovie(movie);
        entity.setText(dto.getText());
        UserDTO userDTO = userService.getProfile();
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
        entity.setUser(user);
    }

}
