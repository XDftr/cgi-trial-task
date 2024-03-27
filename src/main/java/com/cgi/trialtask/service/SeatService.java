package com.cgi.trialtask.service;

import com.cgi.trialtask.dto.SeatResponseDto;
import com.cgi.trialtask.entity.Screening;
import com.cgi.trialtask.entity.Seat;
import com.cgi.trialtask.entity.SeatBooking;
import com.cgi.trialtask.exception.SeatsException;
import com.cgi.trialtask.mapper.SeatMapper;
import com.cgi.trialtask.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final ScreeningService screeningService;
    private final SeatRepository seatRepository;
    private final SeatBookingService seatBookingService;
    private final SeatMapper seatMapper;

    /**
     * Recommends available seats for a screening based on the number of tickets requested.
     *
     * @param screeningId     the ID of the screening
     * @param numberOfTickets the number of tickets requested
     * @return a list of recommended SeatResponseDto objects representing the recommended seats
     * @throws SeatsException if there are not enough seats available or not enough consecutive seats available
     */
    public List<SeatResponseDto> recommendSeats(Integer screeningId, Integer numberOfTickets) {
        Screening screening = screeningService.getScreeningByScreeningId(screeningId);
        List<Seat> allSeats = seatRepository.findAllByCinemaHall(screening.getCinemaHall());
        List<SeatBooking> bookedSeatsForScreening = seatBookingService.getAllSeatBookingsByScreening(screening);

        Set<Seat> bookedSeats = bookedSeatsForScreening.stream()
                .map(SeatBooking::getSeat)
                .collect(Collectors.toSet());

        List<Seat> availableSeats = allSeats.stream()
                .filter(seat -> !bookedSeats.contains(seat))
                .toList();

        if (availableSeats.size() < numberOfTickets) {
            throw new SeatsException("Not enough seats available.");
        }

        int rows = allSeats.stream().mapToInt(Seat::getSeatRow).max().orElse(0);
        int cols = allSeats.stream().mapToInt(Seat::getSeatNumber).max().orElse(0);

        int[][] seatMatrix = new int[rows][cols];

        for (Seat seat : allSeats) {
            seatMatrix[seat.getSeatRow() - 1][seat.getSeatNumber() - 1] = 1;
        }

        for (Seat seat : bookedSeats) {
            seatMatrix[seat.getSeatRow() - 1][seat.getSeatNumber() - 1] = 0;
        }

        List<Seat> recommendedSeats = new ArrayList<>();

        int middleRowIndex = (rows - 1) / 2;
        int middleColIndex = (cols - 1) / 2;

        boolean found = findAndAddSuitableSeats(recommendedSeats, seatMatrix, rows, cols, middleRowIndex, middleColIndex, numberOfTickets);

        if (!found) {
            throw new SeatsException("Not enough consecutive seats available.");
        }


        return seatMapper.toDtoList(recommendedSeats);
    }

    /**
     * Made using ChatGPT
     */
    private boolean findAndAddSuitableSeats(List<Seat> recommendedSeats, int[][] seatMatrix, int rows, int cols,
                                            int middleRowIndex, int middleColIndex, int numberOfTickets) {

        for (int offset = 0; offset <= Math.max(middleRowIndex, middleColIndex); offset++) {
            for (int i = -offset; i <= offset; i++) {
                int currentRow = middleRowIndex + i;
                if (currentRow < 0 || currentRow >= rows) continue;

                for (int j = -offset; j <= offset; j++) {
                    int startSeat = middleColIndex + j;
                    if (startSeat < 0 || startSeat + numberOfTickets > cols) continue;

                    boolean allSeatsAvailable = true;
                    for (int k = 0; k < numberOfTickets; k++) {
                        if (seatMatrix[currentRow][startSeat + k] == 0) {
                            allSeatsAvailable = false;
                            break;
                        }
                    }

                    if (allSeatsAvailable) {
                        for (int k = 0; k < numberOfTickets; k++) {
                            short finalRow = (short) (currentRow + 1);
                            short finalSeatNumber = (short) (startSeat + 1 + k);
                            Optional<Seat> seat = seatRepository.findBySeatRowAndSeatNumber(finalRow, finalSeatNumber);

                            seat.ifPresent(recommendedSeats::add);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
