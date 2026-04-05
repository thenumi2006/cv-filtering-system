package com.cclarke.cv_filtering_system.controller;

import com.cclarke.cv_filtering_system.model.Job;
import com.cclarke.cv_filtering_system.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
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

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job jobDetails) {
        Job job = jobRepository.findById(id).orElseThrow();
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setRequiredSkills(jobDetails.getRequiredSkills());
        job.setExperienceRequired(jobDetails.getExperienceRequired());
        return jobRepository.save(job);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
    }
}