package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.GenreService;
import com.devsuperior.movieflix.services.MovieService;
import com.devsuperior.movieflix.services.ReviewService;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasAnyRole('ROLE_VISITOR','ROLE_MEMBER')")
    @GetMapping
    public ResponseEntity<Page<MovieCardDTO>> findByGenre(@RequestParam(name = "genreId", defaultValue = "0") Long genreId,
            Pageable pageable){
        Page<MovieCardDTO> result = service.findByGenre(genreId, pageable);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyRole('ROLE_VISITOR','ROLE_MEMBER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id){
        MovieDetailsDTO result = service.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findAllReviewsById(@PathVariable Long id){
        List<ReviewDTO> result = reviewService.findAllReviewsById(id);
        if (result.isEmpty()){
            throw new ResourceNotFoundException("Reviews Not Found By Id");
        }
        return ResponseEntity.ok(result);
    }

}
