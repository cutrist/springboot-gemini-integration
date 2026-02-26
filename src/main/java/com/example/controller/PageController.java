package com.example.controller;

import com.example.service.GeminiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PageController {

    private final GeminiService geminiService;

    public PageController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/")
    public String index() {
        return "index2";
    }

    @PostMapping("/chat")
    public String chat(
            @RequestParam String message,
            Model model
    ) {

        String response = geminiService.chat(message);

        model.addAttribute("message", message);
        model.addAttribute("response", response);

        return "index2";
    }

    @PostMapping("/chat-image")
    public String chatImage(
            @RequestParam String message,
            @RequestParam MultipartFile image,
            Model model
    ) {

        String response =
                geminiService.chatWithImage(message, image);

        model.addAttribute("message", message);
        model.addAttribute("response", response);

        return "index2";
    }
}