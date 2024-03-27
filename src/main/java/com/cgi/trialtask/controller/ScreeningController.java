package com.cgi.trialtask.controller;

import com.cgi.trialtask.dto.ScreeningFilter;
import com.cgi.trialtask.dto.ScreeningResponseDto;
import com.cgi.trialtask.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("screening")
@RequiredArgsConstructor
public class ScreeningController {
    private final ScreeningService screeningService;

    /**
     * Retrieves a page of screenings based on the provided filter and pageable information.
     *
     * @param pageable        The pageable information for the search results
     * @param screeningFilter The filter to apply when searching for screenings
     * @return A ResponseEntity containing a Page of ScreeningResponseDto objects that match the filter and pageable information
     */
    @GetMapping
    public ResponseEntity<Page<ScreeningResponseDto>> getScreenings(
            @PageableDefault(sort = "screeningId") Pageable pageable,
            @ModelAttribute ScreeningFilter screeningFilter) {
        return ResponseEntity.ok(screeningService.findScreenings(screeningFilter, pageable));
    }
}
