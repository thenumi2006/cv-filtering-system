package com.cclarke.cv_filtering_system.repository;

import com.cclarke.cv_filtering_system.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // This naming convention tells Spring Data JPA to sort by matchScore descending (High to Low)
    List<Application> findByJobIdOrderByMatchScoreDesc(Long jobId);
}