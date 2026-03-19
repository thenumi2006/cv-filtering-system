package com.cclarke.cv_filtering_system.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String requiredSkills;
    private Integer experienceRequired;

    public Long getJobId() {
        return jobId;
    }

    public Job(Long jobId, String title, String description, String requiredSkills, Integer experienceRequired) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.experienceRequired = experienceRequired;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(Integer experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}