package com.cclarke.cv_filtering_system.model;

import jakarta.persistence.*;

@Entity
public class MatchResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    private Double matchScore;
    private Integer ranking;

    @ManyToOne
    private User candidate;
    @ManyToOne
    private Job job;

    public Double getMatchScore() {
        return matchScore;
    }

    public MatchResult(Long matchId, Double matchScore, Integer ranking, User candidate, Job job) {
        this.matchId = matchId;
        this.matchScore = matchScore;
        this.ranking = ranking;
        this.candidate = candidate;
        this.job = job;
    }

    public void setMatchScore(Double matchScore) {
        this.matchScore = matchScore;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }
}