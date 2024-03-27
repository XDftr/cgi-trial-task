package com.cgi.trialtask.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ScreeningFilter {
    private String genre;
    private Integer ageRestriction;
    private LocalTime startTime;
    private String language;
}
