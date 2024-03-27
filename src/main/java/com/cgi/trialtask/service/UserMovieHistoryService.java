package com.cgi.trialtask.service;

import com.cgi.trialtask.entity.Users;
import com.cgi.trialtask.entity.UserMovieHistory;
import com.cgi.trialtask.repository.UserMovieHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMovieHistoryService {
    private final UserMovieHistoryRepository userMovieHistoryRepository;

    /**
     * Retrieves the movie history for a given user.
     *
     * @param user the user for which to retrieve the movie history
     * @return a list of UserMovieHistory objects representing the movie history for the user
     */
    public List<UserMovieHistory> getUserMovieHistoryByUser(Users user) {
        return userMovieHistoryRepository.findAllByUser(user);
    }
}
