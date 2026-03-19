package com.cclarke.cv_filtering_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    private Long jobId; // Links to the Job

    private String candidateName;

    @Column(columnDefinition = "TEXT")
    private String cvText; // The extracted text from the PDF

    private Double matchScore; // We will fill this with Python later!

    // --- GETTERS AND SETTERS ---
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
    public String getCvText() { return cvText; }
    public void setCvText(String cvText) { this.cvText = cvText; }
    public Double getMatchScore() { return matchScore; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }
}