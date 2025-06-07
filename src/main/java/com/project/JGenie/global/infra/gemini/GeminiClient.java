package com.project.JGenie.global.infra.gemini;

import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import com.project.JGenie.domain.coverletter.repository.UserCoverLetterRepository;
import com.project.JGenie.global.common.util.SecurityUtil;
import com.project.JGenie.global.infra.claude.dto.request.ClaudeAiRequest;
import com.project.JGenie.global.infra.claude.prompt.ClaudeAiPrompt;
import com.project.JGenie.global.infra.gemini.dto.GeminiRequest;
import com.project.JGenie.global.infra.gemini.dto.GeminiResponse;
import com.project.JGenie.global.infra.gemini.enums.GeminiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class GeminiClient {

    private final UserCoverLetterRepository userCoverLetterRepository;
    private final SecurityUtil securityUtil;

    @Value("${gemini.key}")
    private String apiKey;

    private RestTemplate restTemplate;

    @Autowired
    public GeminiClient(UserCoverLetterRepository userCoverLetterRepository, SecurityUtil securityUtil, RestTemplate restTemplate) {
        this.userCoverLetterRepository = userCoverLetterRepository;
        this.securityUtil = securityUtil;
        this.restTemplate = restTemplate;
    }

    public String rqGeminiText(String text) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + GeminiModel.GEMINI_20_FLASH.getModelId() + ":generateContent?key="
                + apiKey;
        GeminiRequest geminiReqDto = new GeminiRequest(text);
        GeminiResponse geminiResDto = restTemplate.postForObject(url, geminiReqDto, GeminiResponse.class);
        return geminiResDto.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    public GeminiResponse rqGeminiTextWithImage(String text, String mimeType, String data){
        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + GeminiModel.GEMINI_25_FLASH.getModelId() + ":generateContent?key="
                + apiKey;

        GeminiRequest geminiReqDto = new GeminiRequest(text, mimeType, data);
        GeminiResponse geminiResDto = restTemplate.postForObject(url, geminiReqDto, GeminiResponse.class);
        return geminiResDto;
    }


    public void AIgenerateCoverLetter(ClaudeAiRequest claudeAiRequest) throws RuntimeException{
        UserCoverLetterEntity userCoverLetterEntity = UserCoverLetterEntity.builder()
                .userId(claudeAiRequest.getUser().getId())
                .userCoverLetterContent(ClaudeAiPrompt.COVER_LETTER_PROMPT(claudeAiRequest))
                .build();


        try {
            log.info(ClaudeAiPrompt.COVER_LETTER_PROMPT(claudeAiRequest));
            String aiResponse = this.rqGeminiText(ClaudeAiPrompt.COVER_LETTER_PROMPT(claudeAiRequest));
            userCoverLetterRepository.save(UserCoverLetterEntity.builder()
                    .userId(claudeAiRequest.getUser().getId())
                    .userCoverLetterContent(securityUtil.encrypt(aiResponse))
                    .build());
        } catch (Exception e) {
            log.error("Claude API call failed: {}", e.getMessage());
        }
    }

}
