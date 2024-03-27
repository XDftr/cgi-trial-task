package com.cgi.trialtask.repository;

import com.cgi.trialtask.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}
