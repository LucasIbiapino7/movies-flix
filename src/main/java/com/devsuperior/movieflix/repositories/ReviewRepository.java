package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.projection.ReviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_review.id, tb_review.text, tb_movie.id AS movieId, tb_user.id AS userId, name, email " +
            "FROM tb_review " +
            "INNER JOIN tb_user ON tb_review.user_id = tb_user.id " +
            "INNER JOIN tb_movie ON tb_review.movie_id = tb_movie.id " +
            "WHERE tb_review.movie_id = :id")
    List<ReviewProjection> searchAllReviewsById(Long id);
}