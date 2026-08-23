package com.ats.engine.controller;

import com.ats.engine.dto.StepResponse;
import com.ats.engine.service.RankingOrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/rank")
@RequiredArgsConstructor
public class RankingController {

    private final RankingOrchestratorService orchestrator;

    @PostMapping("/{task}")
    public CompletableFuture<ResponseEntity<StepResponse>> rank(@PathVariable String task) {
        return orchestrator.processRankingAsync(task)
                .thenApply(ResponseEntity::ok);
    }
}
