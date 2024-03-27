package com.cgi.trialtask.service;

import com.cgi.trialtask.dto.ScreeningFilter;
import com.cgi.trialtask.dto.ScreeningResponseDto;
import com.cgi.trialtask.entity.Genre;
import com.cgi.trialtask.entity.Screening;
import com.cgi.trialtask.exception.NotFoundException;
import com.cgi.trialtask.repository.ScreeningRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieGenreService movieGenreService;

    /**
     * Finds screenings based on the provided filter and pageable information.
     *
     * @param screeningFilter The filter to apply when searching for screenings
     * @param pageable        The pageable information for the search results
     * @return A Page of ScreeningResponseDto objects that match the filter and pageable information
     */
    public Page<ScreeningResponseDto> findScreenings(ScreeningFilter screeningFilter, Pageable pageable) {
        Specification<Screening> specification= filterScreenings(screeningFilter);

        List<ScreeningResponseDto> screeningResponseDtoList = new ArrayList<>();
        Page<Screening> page = screeningRepository.findAll(specification, pageable);

        for (Screening s : page.getContent()) {
            List<Genre> genres = movieGenreService.getGenresByMovie(s.getMovie());
            List<String> genreNames = genres.stream().map(Genre::getGenreName).toList();

            if ((screeningFilter.getStartTime() != null && s.getScreeningTime().toLocalTime().isBefore(screeningFilter.getStartTime()))
                    || (screeningFilter.getGenre() != null && !genreNames.contains(screeningFilter.getGenre()))) {
                continue;
            }

            ScreeningResponseDto screeningResponseDto = new ScreeningResponseDto();
            screeningResponseDto.setScreeningId(s.getScreeningId());
            screeningResponseDto.setMovieTitle(s.getMovie().getTitle());
            screeningResponseDto.setScreeningTime(s.getScreeningTime());
            screeningResponseDto.setGenres(genreNames);

            screeningResponseDtoList.add(screeningResponseDto);
        }
        return new PageImpl<>(screeningResponseDtoList, page.getPageable(), page.getTotalElements());
    }

    /**
     * Retrieves a screening based on the provided screening id.
     *
     * @param id The id of the screening
     * @return The Screening object with the given id
     * @throws NotFoundException if no screening is found with the given id
     */
    public Screening getScreeningByScreeningId(Integer id) {
        return screeningRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No screening with that id")
        );
    }

    private Specification<Screening> filterScreenings(ScreeningFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            LocalDateTime start = LocalDateTime.now();
            LocalDateTime end = LocalDateTime.now().plusDays(7);

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("screeningTime"), start));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("screeningTime"), end));

            if (filter.getAgeRestriction() != null) {
                predicates.add(criteriaBuilder.equal(root.get("movie").get("ageRestriction"),
                        filter.getAgeRestriction()));
            }

            if (filter.getLanguage() != null && !filter.getLanguage().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("movie").get("language").get("languageName"),
                        filter.getLanguage()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
