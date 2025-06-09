package com.project.JGenie.global.infra.claude.prompt;

import com.project.JGenie.domain.career.entity.UserCareerEntity;
import com.project.JGenie.domain.user.entity.UserEntity;
import com.project.JGenie.global.infra.claude.dto.request.ClaudeAiRequest;
import lombok.Getter;

import java.util.List;
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
                8. '지원자 경력' 텍스트를 분석해서 작성자의 글쓰기 습관(실수, 어색함, 문장 형식 등)을 파악하고, 자소서를 생성할때 이를 반영, 오직 문체와 표현만 바꿔서 같은 사람이 직접 쓴 것처럼 작성
                9. 성인도 가끔 실수하는 (ex: 띄어쓰기, 맞춤법, ", ' , .  , 의 사용 등)을 자연스럽게 반영하여 작성.
                
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
                                .map(career -> "--- 경력 정보 ---\n" + career.getCareerContent() + "\n" + career.getCareerTitle())
                                .collect(Collectors.joining("\n\n")),

                        claudeAiRequest.getQuestion1(),
                        claudeAiRequest.getQuestion2(),
                        claudeAiRequest.getQuestion3()
        );

    }

    public static String CAREER_EVALUATION_PROMPT(List<UserCareerEntity> careers) {
        return """
                커리어 평가 전문가
                  제공된 경력 데이터를 바탕으로 현실적이고 보수적인 관점에서 취업 가능성을 평가하고 개선방향을 제시하세요.
                  단정적인 어조 보다는 부드러운 어조를 사용하세요.
                  평가 기준 (매우 엄격하게 적용)
                
                  1-20점: 경력 없음 또는 관련 없는 경험만 있음
                  21-40점: 기초 수준의 경험, 추가 학습 필요
                  41-60점: 어느 정도 경험 있으나 부족한 부분 많음
                  61-80점: 충분한 경험과 실력, 일부 개선점 존재
                  81-100점: 업계 최상위 수준, 완벽한 커리어
                
                  평가 시 고려사항
                
                  실무 경험의 깊이와 질 (가장 중요)
                  기술 스택의 현재성과 실용성
                  프로젝트 규모와 책임 수준
                  업계 표준 대비 경력 기간
                  지속적 학습과 성장 증거
                
                  점수 산정 원칙
                
                  경력 1년 미만: 최대 30점
                  경력 1-3년: 최대 50점
                  경력 3-5년: 최대 70점
                  경력 5년 이상: 상황에 따라 70-100점
                  단순 나열된 기술보다 실제 활용 경험 중시
                  토이 프로젝트는 실무 경험 대비 낮게 평가
                
                  경력이 없거나 평가할 수 없다면 점수를 0점으로 설정하고 분석 내용을 작성하지 마세요.
                 
                  === 경력 데이터 ===
                  %s
                  ================
                  응답은 항상 json 형식으로 작성하며 json 데이터 이외에 어떤것도 포함시키지 마세요
                  {
                      "evaluationMessage": "커리어 개선방향을 제시하세요 (짧게 1문장)",
                      "evaluationScore": "점수 (1-100, 보수적으로 산정)"
                  }
                """.formatted(careers.stream()
                .map(career -> "--- 경력 정보 ---\n" + "[" + career.getCareerType() + "]\n" + career.getCareerTitle() + "\n" + career.getCareerContent())
                .collect(Collectors.joining("\n\n")));
    }
}
