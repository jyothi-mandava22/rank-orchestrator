package com.ats.engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionRequest {
    @JsonProperty("ranked_candidates")   // <-- Tells Jackson to use snake_case in JSON
    private List<String> rankedCandidates = new ArrayList<>();

    @JsonProperty("flagged_candidates")  // <-- Same for optional field
    private List<String> flaggedCandidates = new ArrayList<>();
}