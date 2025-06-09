package com.project.JGenie.global.infra.claude;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.JGenie.domain.career.dto.UserCareerEvaluationDto;
import com.project.JGenie.domain.career.entity.UserCareerEntity;
import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import com.project.JGenie.domain.coverletter.repository.UserCoverLetterRepository;
import com.project.JGenie.global.common.util.SecurityUtil;
import com.project.JGenie.global.infra.claude.dto.request.ClaudeAiRequest;
import com.project.JGenie.global.infra.claude.dto.response.ClaudeAiResponse;
import com.project.JGenie.global.infra.claude.prompt.ClaudeAiPrompt;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ClaudeAiClient {
    private final AnthropicChatModel chatModel; //챗봇 관련 객체
    private final UserCoverLetterRepository userCoverLetterRepository;
    private final SecurityUtil securityUtil;
    private final ObjectMapper objectMapper;
    private final HttpSession session; //세션 객체

    @Autowired
    public ClaudeAiClient(AnthropicChatModel chatModel, UserCoverLetterRepository userCoverLetterRepository, SecurityUtil securityUtil
    , ObjectMapper objectMapper, HttpSession session) { //챗봇 생성자 주입
        this.chatModel = chatModel;
        this.userCoverLetterRepository = userCoverLetterRepository;
        this.securityUtil = securityUtil;
        this.objectMapper = objectMapper;
        this.session = session; //세션 객체 초기화
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
                    .userCoverLetterContent(securityUtil.encrypt(aiResponse))
                    .build());
        } catch (Exception e) {
            log.error("Claude API call failed: {}", e.getMessage());
            throw new RuntimeException("AI career evaluation generation failed");
        }
    }

    public UserCareerEvaluationDto AIgenerateCareerEvaluation(List<UserCareerEntity> careers) {
        try {
            System.out.println(ClaudeAiPrompt.CAREER_EVALUATION_PROMPT(careers));
            String aiResponse = chatModel.call(ClaudeAiPrompt.CAREER_EVALUATION_PROMPT(careers));

            //
            System.out.println("AI Response:\n" + aiResponse);
            //

            UserCareerEvaluationDto userCareerEvaluationDto = objectMapper.readValue(aiResponse, UserCareerEvaluationDto.class);

            //
            System.out.println("파싱 메시지 : "  + userCareerEvaluationDto.getEvaluationMessage());
            System.out.println("파싱 스코어 : " + userCareerEvaluationDto.getEvaluationScore());
            //
            userCareerEvaluationDto.setUserId(session.getAttribute("id").toString());
            return userCareerEvaluationDto;
        } catch (Exception e) {
            return UserCareerEvaluationDto.builder()
                    .userId(session.getAttribute("id").toString())
                    .evaluationMessage("커리어가 부족하여 평가할 수 없습니다! 커리어를 추가해주세요.")
                    .evaluationScore(0)
                    .build();
        }
    }
}
