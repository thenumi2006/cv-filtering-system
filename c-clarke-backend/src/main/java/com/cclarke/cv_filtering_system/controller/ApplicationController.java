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

    @PostMapping("/apply")
    public Application applyForJob(
            @RequestParam("jobId") Long jobId,
            @RequestParam("candidateName") String candidateName,
            @RequestParam("file") MultipartFile file) throws Exception {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        String extractedText = cvService.saveAndExtractText(file);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> aiRequest = new HashMap<>();
        aiRequest.put("cv_text", extractedText);
        aiRequest.put("job_description", job.getDescription());

        Application app = new Application();
        app.setJobId(jobId);
        app.setCandidateName(candidateName);
        app.setCvText(extractedText);

        try {
//             AI answer
            Map<String, Object> aiResponse = restTemplate.postForObject("http://localhost:5000/match", aiRequest, Map.class);


            if (aiResponse != null) {
                app.setMatchScore(Double.valueOf(aiResponse.getOrDefault("match_score", 0).toString()));
                app.setAiSummary(String.valueOf(aiResponse.getOrDefault("summary", "N/A")));

                // Use String.valueOf() for all extraction to prevent ClassCastExceptions
                app.setEmail(String.valueOf(aiResponse.getOrDefault("email", "N/A")));
                app.setPhone(String.valueOf(aiResponse.getOrDefault("phone", "N/A")));
                app.setEducation(String.valueOf(aiResponse.getOrDefault("education", "N/A")));
                app.setSkills(String.valueOf(aiResponse.getOrDefault("skills", "N/A")));
                app.setWorkExperience(String.valueOf(aiResponse.getOrDefault("work_experience", "N/A")));

                app.setReasonSelect(String.valueOf(aiResponse.getOrDefault("reason_select", "N/A")));
                app.setReasonCaution(String.valueOf(aiResponse.getOrDefault("reason_caution", "N/A")));
            }
            applicationRepository.save(app);

            applicationRepository.save(app);

        } catch (Exception e) {
            System.err.println("AI Error: " + e.getMessage());
            app.setMatchScore(0.0);
            app.setAiSummary("AI analysis failed to load.");
        }

        return applicationRepository.save(app);
    }


    @GetMapping("/job/{jobId}")
    public List<Application> getApplicationsByJob(@PathVariable Long jobId) {
        return applicationRepository.findByJobIdOrderByMatchScoreDesc(jobId);
    }
}