package com.project.JGenie.global.infra.claude;

import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import com.project.JGenie.domain.coverletter.repository.UserCoverLetterRepository;
import com.project.JGenie.global.infra.claude.dto.request.ClaudeAiRequest;
import com.project.JGenie.global.infra.claude.dto.response.ClaudeAiResponse;
import com.project.JGenie.global.infra.claude.prompt.ClaudeAiPrompt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClaudeAiClient {
    private final AnthropicChatModel chatModel; //챗봇 관련 객체
    private final UserCoverLetterRepository userCoverLetterRepository;

    @Autowired
    public ClaudeAiClient(AnthropicChatModel chatModel, UserCoverLetterRepository userCoverLetterRepository) { //챗봇 생성자 주입
        this.chatModel = chatModel;
        this.userCoverLetterRepository = userCoverLetterRepository;
    }

//    public void sendChatBot(ClaudeAiRequest claudeAiRequest) {
//        String AiResponse = chatModel.call()
//    }
    public void AIgenerateCoverLetter(ClaudeAiRequest claudeAiRequest) throws RuntimeException{
        UserCoverLetterEntity userCoverLetterEntity = UserCoverLetterEntity.builder()
                .userId(claudeAiRequest.getUser().getId())
                .userCoverLetterContent(ClaudeAiPrompt.COVER_LETTER_PROMPT(claudeAiRequest))
                .build();

        
        try {
            log.info(ClaudeAiPrompt.COVER_LETTER_PROMPT(claudeAiRequest));
            String aiResponse = chatModel.call(ClaudeAiPrompt.COVER_LETTER_PROMPT(claudeAiRequest));
            userCoverLetterRepository.save(UserCoverLetterEntity.builder()
                    .userId(claudeAiRequest.getUser().getId())
                    .userCoverLetterContent(aiResponse)
                    .build());
        } catch (Exception e) {
            log.error("Claude API call failed: {}", e.getMessage());
        }
    }
}
