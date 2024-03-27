package com.cgi.trialtask.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponseDto {
    private Integer movieId;
    private String title;
    private Short ageRestriction;
    private String language;
    private Integer lengthInMinutes;
}
