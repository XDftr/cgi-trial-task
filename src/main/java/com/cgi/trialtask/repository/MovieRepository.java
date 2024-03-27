package com.cgi.trialtask.repository;

import com.cgi.trialtask.entity.Genre;
import com.cgi.trialtask.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("SELECT m FROM Movie m " +
            "JOIN MovieGenre mg ON m.movieId = mg.movie.movieId " +
            "JOIN Genre g ON mg.genre.genreId = g.genreId " +
            "WHERE g = :genre AND " +
            "m.movieId NOT IN (" +
            "SELECT umh.movie.movieId FROM UserMovieHistory umh WHERE umh.user.userId = :userId" +
            ") " +
            "GROUP BY m.movieId " +
            "ORDER BY COUNT(m.movieId) DESC")
    List<Movie> findMoviesByGenreAndNotInUserHistory(@Param("genre") Genre genre, @Param("userId") Integer userId);
}
