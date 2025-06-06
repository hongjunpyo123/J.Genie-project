package com.project.JGenie.global.infra.claude.prompt;

import com.project.JGenie.domain.user.entity.UserEntity;
import com.project.JGenie.global.infra.claude.dto.request.ClaudeAiRequest;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class ClaudeAiPrompt {
    public static String COVER_LETTER_PROMPT(ClaudeAiRequest claudeAiRequest) {
        return """
                당신은 취업 자기소개서 작성 전문가입니다.
                제공된 데이터를 활용하여 맞춤형 자기소개서를 생성해주세요.
                
                === 회사가 원하는 인재상 ===
                회사명: %s
                인재상: %s
                
                === 지원자 기본 정보 ===
                이름: %s
                나이: %s
                전공: %s
                
                === 합격 자소서 데이터 ===
                %s
                
                === 지원자 경력 ===
                %s
                
                === 작성 원칙 ===
                1. 사용자의 실제 커리어 데이터만을 활용하여 작성
                2. 해당 기업의 인재상을 참고하여 방향성 설정
                3. 합격 자소서 예시의 문장 구성, 어투, 분량을 참고
                4. 사실 기반으로만 작성하며 허위 내용 금지
                5. 구체적인 경험과 성과 위주로 서술
                6. 오직 마크다운 형식으로 생성된 자기소개서만 반환
                7. 지원 직무 파악은 사용자의 전공 그리고 경력 정보로 판단
                
                === 응답 형식 ===
                마크다운 형식으로 작성하며 각 질문에 대해 다음과 같이 답변하세요. 질문에 대한 답변이기 때문에 다음과 같은 형식으로 답변하세요.
                {
                ## [1. 질문]
                답변.
                ## [2. 질문]
                답변
                }
                
              
                === 질문 목록 ===
                1. %s
                2. %s
                3. %s
                """.formatted(
                        claudeAiRequest.getCompany().getCompanyName(),
                        claudeAiRequest.getCompany().getTalentProfile(),

                        claudeAiRequest.getUser().getName(),
                        claudeAiRequest.getUser().getAge(),
                        claudeAiRequest.getUser().getMajor(),

                        claudeAiRequest.getExampleCoverLetter().stream()
                                .map(letter -> "--- 합격한 자소서 예시 ---\n" + letter.getExampleCoverLetterContent())
                                .collect(Collectors.joining("\n\n")),

                        claudeAiRequest.getUserCareer().stream()
                                .map(career -> "--- 경력 정보 ---\n" + career.getCareerContent())
                                .collect(Collectors.joining("\n\n")),

                        claudeAiRequest.getQuestion1(),
                        claudeAiRequest.getQuestion2(),
                        claudeAiRequest.getQuestion3()
        );

    }
}
