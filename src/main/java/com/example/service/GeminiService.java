package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class GeminiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.model}")
    private String model;

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // TEXT ONLY
    public String chat(String prompt) {

        try {

            Map<String, Object> body = Map.of(
                    "model", model,
                    "stream", false,
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt
                            )
                    )
            );

            return sendRequest(body);

        } catch (Exception e) {
            throw new RuntimeException("Gemini text failed: " + e.getMessage());
        }
    }

    // TEXT + IMAGE
    public String chatWithImage(String prompt, MultipartFile image) {

        try {

            String base64Image =
                    Base64.getEncoder().encodeToString(image.getBytes());

            Map<String, Object> body = Map.of(
                    "model", model,
                    "stream", false,
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt,
                                    "images", List.of(base64Image)
                            )
                    )
            );

            return sendRequest(body);

        } catch (Exception e) {
            throw new RuntimeException("Gemini image failed: " + e.getMessage());
        }
    }

    private String sendRequest(Map<String, Object> body) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        apiUrl,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        Map json = mapper.readValue(response.getBody(), Map.class);

        Map message = (Map) json.get("message");

        return message.get("content").toString();
    }
}