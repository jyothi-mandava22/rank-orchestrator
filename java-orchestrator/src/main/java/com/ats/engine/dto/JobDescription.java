package com.ats.engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDescription {
    @JsonProperty("job_id")
    private String jobId;

    @JsonProperty("role")
    private String role;

    @JsonProperty("must_have")
    private List<SkillWeight> mustHave;

    @JsonProperty("nice_to_have")
    private List<SkillWeight> niceToHave;

    @JsonProperty("min_experience")
    private int minExperience;
}