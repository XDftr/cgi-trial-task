package com.cgi.trialtask.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ScreeningResponseDto {
    private Integer screeningId;
    private String movieTitle;
    private LocalDateTime screeningTime;
    private List<String> genres;
}
