package com.ats.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingHistoryRepo extends JpaRepository<RankingHistory, Long> {
}
