package com.cgi.trialtask.mapper;

import com.cgi.trialtask.dto.SeatResponseDto;
import com.cgi.trialtask.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SeatMapper {

    SeatResponseDto toDto(Seat seat);

    List<SeatResponseDto> toDtoList(List<Seat> seatList);
}
