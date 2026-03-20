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
                // scores and reasons
                app.setMatchScore(Double.valueOf(aiResponse.getOrDefault("match_score", 0).toString()));
                app.setReasonSelect(aiResponse.getOrDefault("reason_select", "N/A").toString());
                app.setReasonCaution(aiResponse.getOrDefault("reason_caution", "N/A").toString());
                app.setAiSummary(aiResponse.getOrDefault("summary", "N/A").toString());

                // candidate information
                app.setEmail(aiResponse.getOrDefault("email", "N/A").toString());
                app.setPhone(aiResponse.getOrDefault("phone", "N/A").toString());
                app.setEducation(aiResponse.getOrDefault("education", "N/A").toString());
                app.setSkills(aiResponse.getOrDefault("skills", "N/A").toString());
                app.setWorkExperience(aiResponse.getOrDefault("work_experience", "N/A").toString());
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