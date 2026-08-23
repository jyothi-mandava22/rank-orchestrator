package com.ats.engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @JsonProperty("candidate_id")
    private String candidateId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("skills")
    private List<String> skills;

    @JsonProperty("experience")
    private int experience;

    @JsonProperty("education")
    private String education;
}