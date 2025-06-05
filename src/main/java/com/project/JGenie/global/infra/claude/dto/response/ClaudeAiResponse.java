package com.project.JGenie.global.infra.claude.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClaudeAiResponse {
    String responseMessage; //챗봇의 응답 메시지
}
