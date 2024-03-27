package com.cgi.trialtask.repository;

import com.cgi.trialtask.entity.Screening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;

public interface ScreeningRepository extends JpaRepository<Screening, Integer>, JpaSpecificationExecutor<Screening> {
    Page<Screening> findAllByScreeningTimeAfterAndScreeningTimeBefore(LocalDateTime start, LocalDateTime end, Pageable pageable);

}
