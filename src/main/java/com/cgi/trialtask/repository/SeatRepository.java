package com.cgi.trialtask.repository;

import com.cgi.trialtask.entity.CinemaHall;
import com.cgi.trialtask.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllByCinemaHall(CinemaHall cinemaHall);

    Optional<Seat> findBySeatRowAndSeatNumber(Short row, Short col);
}
