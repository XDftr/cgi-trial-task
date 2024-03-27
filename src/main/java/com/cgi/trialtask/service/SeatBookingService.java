package com.cgi.trialtask.service;

import com.cgi.trialtask.entity.Screening;
import com.cgi.trialtask.entity.SeatBooking;
import com.cgi.trialtask.repository.SeatBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatBookingService {
    private final SeatBookingRepository seatBookingRepository;

    /**
     * Retrieves a list of all seat bookings for a given screening.
     *
     * @param screening the screening for which to retrieve seat bookings
     * @return a list of SeatBooking objects representing the seat bookings for the screening
     */
    public List<SeatBooking> getAllSeatBookingsByScreening(Screening screening) {
        return seatBookingRepository.findAllByScreening(screening);
    }
}
