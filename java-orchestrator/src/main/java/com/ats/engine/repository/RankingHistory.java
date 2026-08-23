package com.ats.engine.repository;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ranking_history")
@Data
public class RankingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskType;
    private double reward;
    private LocalDateTime timestamp = LocalDateTime.now();
    @Column(columnDefinition = "TEXT")
    private String rankedIds;
}
