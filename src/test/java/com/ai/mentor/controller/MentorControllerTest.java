package com.ai.mentor.controller;

import com.ai.mentor.mentor.MentorAgent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MentorControllerTest {

    @Test
    void chatSanitizesModelResponse() {
        MentorAgent mentorAgent = mock(MentorAgent.class);
        when(mentorAgent.chat("user-1", "hello"))
                .thenReturn("<script>alert('x')</script>Let\u2019s **go** *now*.\u2014");

        MentorController controller = new MentorController(mentorAgent);

        MentorController.ChatResponse response = controller.chat(
                "user-1",
                new MentorController.ChatRequest("hello"));

        assertEquals("Let's go now.-", response.response());
    }
}
