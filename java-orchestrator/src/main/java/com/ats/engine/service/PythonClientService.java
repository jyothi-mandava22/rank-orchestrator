package com.ats.engine.service;

import com.ats.engine.dto.ActionRequest;
import com.ats.engine.dto.ResetResponse;
import com.ats.engine.dto.StepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
@Slf4j
public class PythonClientService {

    private final RestTemplate restTemplate;
    private final String pythonBaseUrl;

    public PythonClientService(RestTemplate restTemplate,
                               @Value("${python.service.url:http://python-ranker:7860}") String url) {
        this.restTemplate = restTemplate;
        this.pythonBaseUrl = url;
    }

    public ResetResponse resetEnvironment(String task) {
        String url = pythonBaseUrl + "/reset";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("task", task), headers);

        log.info("Calling Python /reset for task: {}", task);
        ResponseEntity<ResetResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, request, ResetResponse.class
        );
        return response.getBody();
    }

    public StepResponse stepEnvironment(ActionRequest action) {
        String url = pythonBaseUrl + "/step";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ActionRequest> request = new HttpEntity<>(action, headers);

        log.info("Calling Python /step with {} candidates", action.getRankedCandidates().size());
        ResponseEntity<StepResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, request, StepResponse.class
        );
        return response.getBody();
    }
}
