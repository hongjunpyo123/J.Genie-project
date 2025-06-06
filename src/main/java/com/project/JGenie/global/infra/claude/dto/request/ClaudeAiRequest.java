package com.project.JGenie.global.infra.claude.dto.request;

import com.project.JGenie.domain.company.entity.CompanyEntity;
import com.project.JGenie.domain.coverletter.entity.ExampleCoverLetterEntity;
import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import com.project.JGenie.domain.career.entity.UserCareerEntity;
import com.project.JGenie.domain.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClaudeAiRequest {
    private UserEntity user; // 사용자 정보
    private CompanyEntity company; // 회사 정보 목록 - 회사명, 인재상, 기업정보 등
    private List<UserCareerEntity> userCareer; // 사용자 경력 정보
    private List<ExampleCoverLetterEntity> exampleCoverLetter; // 샘플 자기소개서 목록
    private String question1; // 질문 목록1
    private String question2; // 질문 목록2
    private String question3; // 질문 목록3
}
