package com.ai.mentor.controller;

import com.ai.mentor.mentor.MentorAgent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentor")
@ConditionalOnBean(MentorAgent.class)
public class MentorController {

    private final MentorAgent mentorAgent;

    public MentorController(MentorAgent mentorAgent) {
        this.mentorAgent = mentorAgent;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestParam(defaultValue = "default-user") String userId,
                             @RequestBody ChatRequest request) {
        String response = mentorAgent.chat(userId, request.message());
        return new ChatResponse(sanitizeResponse(response));
    }

    private String sanitizeResponse(String response) {
        if (response == null) {
            return "";
        }

        return response
                .replaceAll("(?is)<(script|style)\\b[^>]*>.*?</\\1>", "")
                .replaceAll("(?s)<[^>]*>", "")
                .replace("**", "")
                .replace("*", "")
                .replace('\u2018', '\'')
                .replace('\u2019', '\'')
                .replace('\u201C', '"')
                .replace('\u201D', '"')
                .replace('\u2013', '-')
                .replace('\u2014', '-')
                .replace("\u2026", "...")
                .replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]", "")
                .replaceAll("[^\\x00-\\x7F]", "")
                .replaceAll("[ \\t]+\\r?\\n", "\n")
                .replaceAll("\\n{3,}", "\n\n")
                .strip();
    }

    public record ChatRequest(String message) {}
    public record ChatResponse(String response) {}
}