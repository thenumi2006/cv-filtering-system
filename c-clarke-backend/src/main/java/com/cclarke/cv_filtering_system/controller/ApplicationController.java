package com.cclarke.cv_filtering_system.controller;

import com.cclarke.cv_filtering_system.model.Application;
import com.cclarke.cv_filtering_system.model.Job;
import com.cclarke.cv_filtering_system.repository.ApplicationRepository;
import com.cclarke.cv_filtering_system.repository.JobRepository;
import com.cclarke.cv_filtering_system.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CVService cvService;
    private final String PYTHON_AI_URL = "http://localhost:5000/match";

    // 1. Candidate applies for a job
    @PostMapping("/apply")
    public Application applyForJob(
            @RequestParam("jobId") Long jobId,
            @RequestParam("candidateName") String candidateName,
            @RequestParam("file") MultipartFile file) throws Exception {

        // 1. Get Job details for the description
        Job job = jobRepository.findById(jobId).orElseThrow();

        // 2. Extract CV Text
        String extractedText = cvService.saveAndExtractText(file);

        // 3. Call Python AI for the score
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> aiRequest = new HashMap<>();
        aiRequest.put("cv_text", extractedText);
        aiRequest.put("job_description", job.getDescription());

        Double score = 0.0;
        try {
            Map<String, Object> aiResponse = restTemplate.postForObject(PYTHON_AI_URL, aiRequest, Map.class);
            score = (Double) aiResponse.get("match_score");
        } catch (Exception e) {
            System.out.println("AI Service down, using 0.0 score");
        }

        // 4. Save Application with Score
        Application app = new Application();
        app.setJobId(jobId);
        app.setCandidateName(candidateName);
        app.setCvText(extractedText);
        app.setMatchScore(score); // Now we have a real score!

        return applicationRepository.save(app);
    }


    // 2. HR Admin views candidates for a specific job
    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsForJob(@PathVariable Long jobId) {
        return applicationRepository.findByJobIdOrderByMatchScoreDesc(jobId);
    }
}