package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.projection.ReviewProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private AuthService authService;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public List<ReviewDTO> findAllReviewsById(Long id) {
        List<ReviewProjection> result = repository.searchAllReviewsById(id);
        return result.stream().map(x -> new ReviewDTO(x)).toList();
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        try {
            User user = authService.authenticated();
            Review newReview = new Review();
            newReview.setText(dto.getText());
            newReview.setUser(user);
            newReview.setMovie(movieRepository.getReferenceById(dto.getMovieId()));
            newReview = repository.save(newReview);
            return new ReviewDTO(newReview);
        }catch (DataIntegrityViolationException e){
            throw new ResourceNotFoundException("Not Found");
        }
    }
}
