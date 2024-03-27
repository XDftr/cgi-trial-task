package com.cgi.trialtask.controller;

import com.cgi.trialtask.dto.MovieResponseDto;
import com.cgi.trialtask.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    /**
     * Retrieves a list of recommended movies for the given user based on their movie history.
     *
     * @param userId the ID of the user
     * @return a ResponseEntity containing a list of recommended movies
     */
    @GetMapping("recommend/{userId}")
    public ResponseEntity<List<MovieResponseDto>> getMovieRecommendations(@PathVariable Integer userId) {
        return ResponseEntity.ok(movieService.getMovieRecommendations(userId));
    }

}
