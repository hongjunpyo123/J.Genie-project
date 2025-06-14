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
                8. '지원자 경력' 데이터에서 작성자의 개인적 글쓰기 패턴(문체, 어휘선택, 문장구조, 구두점 사용습관, 자연스러운 실수 등)을 학습하고, 생성되는 자소서에 동일한 패턴을 완벽히 재현하여 같은 사람이 작성한 것처럼 만들어라
                9. 완벽한 AI 글쓰기가 아닌 실제 사람의 자연스러운 불완전성(띄어쓰기, 구두점, 문장전환의 어색함 등)을 적절히 반영하되, 원본 데이터에서 관찰된 개인 습관을 우선적으로 따라해라.
                
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
                  응답은 순수한 json 형태로만 반환하세요(마크다운 언어 등 금지)
                  evaluationMessage : 커리어 개선방향을 제시하세요 (짧게 1문장)
                  evaluationScore : 점수 (1-100, 보수적으로 산정)
                """.formatted(careers.stream()
                .map(career -> "--- 경력 정보 ---\n" + "[" + career.getCareerType() + "]\n" + career.getCareerTitle() + "\n" + career.getCareerContent())
                .collect(Collectors.joining("\n\n")));
    }
}
