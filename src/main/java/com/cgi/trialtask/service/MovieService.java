package com.cgi.trialtask.service;

import com.cgi.trialtask.dto.MovieResponseDto;
import com.cgi.trialtask.entity.Genre;
import com.cgi.trialtask.entity.Movie;
import com.cgi.trialtask.entity.Users;
import com.cgi.trialtask.entity.UserMovieHistory;
import com.cgi.trialtask.mapper.MovieMapper;
import com.cgi.trialtask.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final UserMovieHistoryService userMovieHistoryService;
    private final UsersService usersService;
    private final MovieGenreService movieGenreService;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    /**
     * Retrieves a list of recommended movies for the given user based on their movie history.
     *
     * @param userId the ID of the user
     * @return a list of recommended movies
     */
    public List<MovieResponseDto> getMovieRecommendations(Integer userId) {
        Users user = usersService.getUserByUserId(userId);
        List<UserMovieHistory> history = userMovieHistoryService.getUserMovieHistoryByUser(user);

        Map<Genre, Long> genreFrequency = history.stream()
                .map(UserMovieHistory::getMovie)
                .flatMap(movie -> movieGenreService.getGenresByMovie(movie).stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Genre> rankedGenres = genreFrequency.entrySet().stream()
                .sorted(Map.Entry.<Genre, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        List<Movie> recommendedMovies = new ArrayList<>();
        for (Genre genre : rankedGenres) {
            if (recommendedMovies.size() >= 5) {
                break;
            }
            List<Movie> moviesInGenre = movieRepository.findMoviesByGenreAndNotInUserHistory(genre, userId);
            recommendedMovies.addAll(moviesInGenre);
        }

        return movieMapper.toDtoList(recommendedMovies.stream().distinct().limit(5).toList());
    }
}
