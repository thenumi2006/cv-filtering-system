package com.cclarke.cv_filtering_system.controller;

import com.cclarke.cv_filtering_system.model.Application;
import com.cclarke.cv_filtering_system.model.Job;
import com.cclarke.cv_filtering_system.repository.ApplicationRepository;
import com.cclarke.cv_filtering_system.repository.JobRepository;
import com.cclarke.cv_filtering_system.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CVService cvService;

    private final String PYTHON_AI_URL = "http://localhost:5000/match";

    @PostMapping("/apply")
    public ResponseEntity<Application> applyForJob(
            @RequestParam("jobId") Long jobId,
            @RequestParam("candidateName") String candidateName,
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file) {

        try {

            Job job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found"));

            String extractedText = cvService.saveAndExtractText(file);

            Application app = new Application();
            app.setJobId(jobId);
            app.setUserId(userId);
            app.setCandidateName(candidateName);
            app.setCvText(extractedText);
            app.setJobTitle(job.getTitle());

            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> aiRequest = new HashMap<>();
            aiRequest.put("cv_text", extractedText);
            aiRequest.put("job_description", job.getDescription());

            try {
                Map<String, Object> aiResponse = restTemplate.postForObject(PYTHON_AI_URL, aiRequest, Map.class);

                if (aiResponse != null) {
                    Object score = aiResponse.getOrDefault("match_score", 0);
                    app.setMatchScore(Double.valueOf(score.toString()));

                    app.setAiSummary(String.valueOf(aiResponse.getOrDefault("summary", "N/A")));
                    app.setEmail(String.valueOf(aiResponse.getOrDefault("email", "N/A")));
                    app.setPhone(String.valueOf(aiResponse.getOrDefault("phone", "N/A")));
                    app.setEducation(String.valueOf(aiResponse.getOrDefault("education", "N/A")));
                    app.setSkills(String.valueOf(aiResponse.getOrDefault("skills", "N/A")));

                    app.setReasonSelect(String.valueOf(aiResponse.getOrDefault("reason_select", "N/A")));
                    app.setReasonCaution(String.valueOf(aiResponse.getOrDefault("reason_caution", "N/A")));
                }
            } catch (Exception e) {
                System.err.println("AI Connection Error: " + e.getMessage());
                app.setAiSummary("AI Connection Error. Please check model ID.");
                app.setMatchScore(0.0);
            }

            Application savedApp = applicationRepository.save(app);
            return ResponseEntity.ok(savedApp);

        } catch (Exception e) {
            System.err.println("System Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsByJobId(@PathVariable Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    @GetMapping("/user/{userId}")
    public List<Application> getApplicationsByUserId(@PathVariable Long userId) {
        return applicationRepository.findByUserId(userId);
    }
}