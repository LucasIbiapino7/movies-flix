package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.projection.ReviewProjection;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Transactional
    public List<ReviewDTO> findAllReviewsById(Long id) {
        List<ReviewProjection> result = repository.searchAllReviewsById(id);
        return result.stream().map(x -> new ReviewDTO(x)).toList();
    }

}
