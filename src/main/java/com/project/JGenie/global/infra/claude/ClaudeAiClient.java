package com.project.JGenie.global.infra.claude;

import com.project.JGenie.global.infra.claude.dto.request.ClaudeAiRequest;
import com.project.JGenie.global.infra.claude.dto.response.ClaudeAiResponse;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaudeAiClient {
    private final AnthropicChatModel chatModel; //챗봇 관련 객체

    @Autowired
    public ClaudeAiClient(AnthropicChatModel chatModel) { //챗봇 생성자 주입
        this.chatModel = chatModel;
    }

    public ClaudeAiResponse sendChatBot(ClaudeAiRequest claudeAiRequest) {
        return null;
    }
}
