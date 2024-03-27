package com.cgi.trialtask.repository;

import com.cgi.trialtask.entity.Movie;
import com.cgi.trialtask.entity.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer> {
    List<MovieGenre> findAllByMovie(Movie movie);
}
