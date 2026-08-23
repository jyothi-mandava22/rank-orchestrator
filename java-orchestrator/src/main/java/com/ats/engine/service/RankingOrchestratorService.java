package com.ats.engine.service;

import com.ats.engine.dto.ActionRequest;
import com.ats.engine.dto.JobDescription;
import com.ats.engine.dto.ResetResponse;
import com.ats.engine.dto.StepResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankingOrchestratorService {

    private final PythonClientService pythonClient;
    private final JavaRankingEngine javaRankingEngine;

    @Async("taskExecutor")
    public CompletableFuture<StepResponse> processRankingAsync(String task) {
        log.info("Starting async ranking for task: {}", task);

        return CompletableFuture.supplyAsync(() -> {
            try {
                ResetResponse reset = pythonClient.resetEnvironment(task);
                JobDescription job = reset.getJobDescription();
                List<com.ats.engine.dto.Candidate> candidates = reset.getCandidates();

                List<String> javaRanking = javaRankingEngine.rankCandidates(job, candidates);

                ActionRequest action = new ActionRequest();
                action.setRankedCandidates(javaRanking);

                if ("hard".equals(task)) {
                    log.info("Hard task: relying on Python LLM verification layer.");
                }

                StepResponse stepResponse = pythonClient.stepEnvironment(action);
                log.info("Async ranking complete. Reward: {}", stepResponse.getReward());

                // TODO: Save to PostgreSQL using RankingHistoryRepo here

                return stepResponse;

            } catch (Exception e) {
                log.error("Async ranking failed: {}", e.getMessage(), e);
                StepResponse fallback = new StepResponse();
                fallback.setReward(0.10);
                fallback.setDone(true);
                return fallback;
            }
        });
    }
}
