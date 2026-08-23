package com.ats.engine.service;

import com.ats.engine.dto.Candidate;
import com.ats.engine.dto.JobDescription;
import com.ats.engine.dto.SkillWeight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j  // <-- This gives us logging!
public class JavaRankingEngine {

    private static final Map<String, String> SKILL_EQUIVALENCES = Map.of(
            "Scientific Computing", "Python",
            "ML Engineering", "Machine Learning",
            "Cloud Infrastructure", "AWS"
    );

    public List<String> rankCandidates(JobDescription job, List<Candidate> candidates) {
        // --- DEBUG CHECKS ---
        if (job == null) {
            log.error("Job is null!");
            return new ArrayList<>();
        }
        if (candidates == null) {
            log.error("Candidates list is null!");
            return new ArrayList<>();
        }
        if (candidates.isEmpty()) {
            log.warn("Candidates list is empty!");
            return new ArrayList<>();
        }

        log.info("Ranking {} candidates for job: {}", candidates.size(), job.getRole());

        Map<String, Integer> scores = new HashMap<>();

        for (Candidate c : candidates) {
            // Check for null skills
            if (c.getSkills() == null) {
                log.warn("Candidate {} has null skills, skipping!", c.getCandidateId());
                continue;
            }

            Set<String> expandedSkills = new HashSet<>(c.getSkills());
            for (String skill : c.getSkills()) {
                if (SKILL_EQUIVALENCES.containsKey(skill)) {
                    expandedSkills.add(SKILL_EQUIVALENCES.get(skill));
                }
            }

            int score = 0;
            // Must-have weights
            if (job.getMustHave() != null) {
                for (SkillWeight req : job.getMustHave()) {
                    if (expandedSkills.contains(req.getSkill())) {
                        score += req.getWeight();
                    }
                }
            }

            // Nice-to-have weights
            if (job.getNiceToHave() != null) {
                for (SkillWeight req : job.getNiceToHave()) {
                    if (expandedSkills.contains(req.getSkill())) {
                        score += req.getWeight();
                    }
                }
            }

            // Experience bonus
            if (c.getExperience() >= job.getMinExperience()) {
                score += 2;
            }

            scores.put(c.getCandidateId(), score);
        }

        if (scores.isEmpty()) {
            log.error("No scores computed for any candidate!");
            return new ArrayList<>();
        }

        // Sort: Descending score, ascending ID for ties
        List<String> rankedIds = new ArrayList<>(scores.keySet());
        rankedIds.sort((id1, id2) -> {
            int cmp = scores.get(id2) - scores.get(id1);
            if (cmp == 0) return id1.compareTo(id2);
            return cmp;
        });

        log.info("Generated ranking of size: {}", rankedIds.size());
        log.info("Top 3 ranked IDs: {}", rankedIds.stream().limit(3).toList());

        return rankedIds;
    }
}