package com.cclarke.cv_filtering_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String requiredSkills;
    private Integer experienceRequired;


    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }
    public Integer getExperienceRequired() { return experienceRequired; }
    public void setExperienceRequired(Integer experienceRequired) { this.experienceRequired = experienceRequired; }
}