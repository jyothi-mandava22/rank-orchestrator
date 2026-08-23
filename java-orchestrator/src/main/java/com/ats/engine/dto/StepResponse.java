package com.ats.engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepResponse {
    @JsonProperty("observation")
    private Observation observation;

    @JsonProperty("reward")
    private double reward;

    @JsonProperty("done")
    private boolean done;

    @JsonProperty("info")
    private Map<String, Object> info;
}