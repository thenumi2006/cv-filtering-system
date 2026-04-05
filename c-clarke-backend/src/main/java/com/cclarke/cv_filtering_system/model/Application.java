package com.cclarke.cv_filtering_system.model;

import jakarta.persistence.*;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    private Long jobId;
    private Long userId;
    private String candidateName;
    private Long id;
    private String jobTitle;

    @Column(columnDefinition = "TEXT")
    private String cvText;

    private Double matchScore;

    @Column(columnDefinition = "TEXT")
    private String aiSummary;

    @Column(columnDefinition = "TEXT")
    private String reasonSelect;

    @Column(columnDefinition = "TEXT")
    private String reasonCaution;

    private String email;
    private String phone;
    @Column(columnDefinition = "TEXT")
    private String education;
    @Column(columnDefinition = "TEXT")
    private String topSkills;

    @Column(columnDefinition = "TEXT")
    private String skills;
    @Column(columnDefinition = "TEXT")
    private String workExperience;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCvText() {
        return cvText;
    }

    public void setCvText(String cvText) {
        this.cvText = cvText;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Double matchScore) {
        this.matchScore = matchScore;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public String getReasonSelect() {
        return reasonSelect;
    }

    public void setReasonSelect(String reasonSelect) {
        this.reasonSelect = reasonSelect;
    }

    public String getReasonCaution() {
        return reasonCaution;
    }

    public void setReasonCaution(String reasonCaution) {
        this.reasonCaution = reasonCaution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTopSkills() {
        return topSkills;
    }

    public void setTopSkills(String topSkills) {
        this.topSkills = topSkills;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }
}