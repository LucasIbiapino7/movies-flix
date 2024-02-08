package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projection.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = "SELECT tb_movie.id, title, sub_title AS subTitle, movie_year AS yearMovie, img_url AS imgUrl " +
            "FROM tb_movie " +
            "INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id " +
            "WHERE :genreId IS NULL OR tb_genre.id IN :genreId " +
            "ORDER BY tb_movie.title",
            countQuery = "SELECT COUNT(*) FROM (" +
                    "SELECT tb_movie.id, title, sub_title AS subTitle, movie_year AS yearMovie, img_url AS imgUrl " +
                    "FROM tb_movie " +
                    "INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id " +
                    "WHERE :genreId IS NULL OR tb_genre.id IN :genreId " +
                    "ORDER BY tb_movie.title" +
                    ") AS tb_result")
    Page<MovieProjection> searchByGenre(List<Long> genreId, Pageable pageable);

    @Query("SELECT obj " +
            "FROM Movie obj " +
            "JOIN FETCH obj.genre " +
            "WHERE obj.id = :id")
    Optional<Movie> searchMovieWithGenre(Long id);
}
