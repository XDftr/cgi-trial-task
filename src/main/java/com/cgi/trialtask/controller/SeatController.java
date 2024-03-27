package com.cgi.trialtask.controller;

import com.cgi.trialtask.dto.SeatResponseDto;
import com.cgi.trialtask.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    /**
     * Retrieves a list of recommended seats for a given screening and number of tickets.
     *
     * @param screeningId     the ID of the screening
     * @param numberOfTickets the number of tickets requested
     * @return a ResponseEntity containing a list of recommended SeatResponseDto objects representing the recommended seats
     */
    @GetMapping("recommended")
    public ResponseEntity<List<SeatResponseDto>> getRecommendedSeats(
            @RequestParam(name = "screening") Integer screeningId,
            @RequestParam(name = "tickets") Integer numberOfTickets) {
        return ResponseEntity.ok(seatService.recommendSeats(screeningId,numberOfTickets));
    }
}
