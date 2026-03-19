package com.cclarke.cv_filtering_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CV {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvId;
    private String filePath;
    @Column(columnDefinition = "TEXT")
    private String extractedText;
    private LocalDateTime uploadedDate = LocalDateTime.now();

    public CV(Long cvId, String filePath, String extractedText, LocalDateTime uploadedDate, User candidate) {
        this.cvId = cvId;
        this.filePath = filePath;
        this.extractedText = extractedText;
        this.uploadedDate = uploadedDate;
        this.candidate = candidate;
    }

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;

    public Long getCvId() {
        return cvId;
    }

    public void setCvId(Long cvId) {
        this.cvId = cvId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }
}