package com.cgi.trialtask.service;

import com.cgi.trialtask.entity.Genre;
import com.cgi.trialtask.entity.Movie;
import com.cgi.trialtask.entity.MovieGenre;
import com.cgi.trialtask.repository.MovieGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieGenreService {
    private final MovieGenreRepository movieGenreRepository;

    /**
     * Retrieves the list of genres associated with a movie.
     *
     * @param movie The movie for which to retrieve the genres
     * @return A list of Genre objects associated with the movie
     */
    public List<Genre> getGenresByMovie(Movie movie) {
        return movieGenreRepository.findAllByMovie(movie)
                .stream()
                .map(MovieGenre::getGenre)
                .toList();
    }
}
