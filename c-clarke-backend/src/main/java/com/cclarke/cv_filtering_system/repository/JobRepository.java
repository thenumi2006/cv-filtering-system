package com.cclarke.cv_filtering_system.repository;

import com.cclarke.cv_filtering_system.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

}