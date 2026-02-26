package com.example.controller;

import com.example.dto.ChatRequest;
import com.example.dto.ChatResponse;
import com.example.service.GeminiService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    // TEXT ONLY API
    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String response =
                geminiService.chat(request.getMessage());

        return new ChatResponse(response);
    }

    // IMAGE + TEXT API
    @PostMapping("/chat-with-image")
    public ChatResponse chatWithImage(
            @RequestParam String message,
            @RequestParam MultipartFile image
    ) {

        String response =
                geminiService.chatWithImage(message, image);

        return new ChatResponse(response);
    }
}