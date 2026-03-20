package com.cclarke.cv_filtering_system.controller;

import com.cclarke.cv_filtering_system.model.Job;
import com.cclarke.cv_filtering_system.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;


    @PostMapping
    public Job createJob(@RequestBody Job job) {

        return jobRepository.save(job);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}