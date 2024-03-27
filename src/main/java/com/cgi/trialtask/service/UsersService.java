package com.cgi.trialtask.service;

import com.cgi.trialtask.entity.Users;
import com.cgi.trialtask.exception.NotFoundException;
import com.cgi.trialtask.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    /**
     * Retrieves a User object by providing the user ID.
     *
     * @param id the ID of the user
     * @return the User object corresponding to the provided ID
     * @throws NotFoundException if no user exists with the provided ID
     */
    public Users getUserByUserId(Integer id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No user exists with that id")
        );
    }
}
