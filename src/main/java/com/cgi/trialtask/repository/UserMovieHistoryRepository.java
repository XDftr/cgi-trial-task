package com.cgi.trialtask.repository;

import com.cgi.trialtask.entity.Users;
import com.cgi.trialtask.entity.UserMovieHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMovieHistoryRepository extends JpaRepository<UserMovieHistory, Integer> {
    List<UserMovieHistory> findAllByUser(Users user);
}
