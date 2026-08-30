package com.ai.mentor.mentor.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class GoalTrackingTools {

    @Tool("Logs a structured long-term goal with an optional target deadline")
    public String registerGoal(String goalDescription, String deadline) {
        // Integrate with PostgreSQL / JPA here
        return "SUCCESS: Logged goal '" + goalDescription + "' with target date " + deadline;
    }

    @Tool("Flags a cognitive distortion or limiting belief voiced by the user for accountability tracking")
    public String flagLimitingBelief(String limitingBelief) {
        // Log limiting belief for future session reflection
        return "SUCCESS: Recorded limiting belief '" + limitingBelief + "' for future reflection.";
    }
}