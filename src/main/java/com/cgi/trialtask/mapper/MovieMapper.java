package com.cgi.trialtask.mapper;

import com.cgi.trialtask.dto.MovieResponseDto;
import com.cgi.trialtask.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {

    @Mapping(target = "language", source = "language.languageName")
    MovieResponseDto toDto(Movie movie);

    List<MovieResponseDto> toDtoList(List<Movie> movieList);
}
