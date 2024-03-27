package com.cgi.trialtask.repository;

import com.cgi.trialtask.entity.Screening;
import com.cgi.trialtask.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatBookingRepository extends JpaRepository<SeatBooking, Integer> {
    List<SeatBooking> findAllByScreening(Screening screening);
}
