package com.ats.engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Observation {
    @JsonProperty("job_description")
    private JobDescription jobDescription;

    @JsonProperty("candidates")
    private List<Candidate> candidates;

    @JsonProperty("step")
    private int step;

    @JsonProperty("history")
    private List<Map<String, Object>> history;
}