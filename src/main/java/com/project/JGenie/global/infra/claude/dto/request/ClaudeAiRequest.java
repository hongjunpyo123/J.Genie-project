package com.project.JGenie.global.infra.claude.dto.request;

import com.project.JGenie.domain.company.entity.CompanyEntity;
import com.project.JGenie.domain.coverletter.entity.SampleCoverLetterEntity;
import com.project.JGenie.domain.user.entity.UserCareerEntity;
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
    private CompanyEntity companies; // 회사 정보 목록 - 회사명, 인재상, 기업정보 등
    private List<UserCareerEntity> userCareer; // 사용자 경력 정보
    private List<SampleCoverLetterEntity> sampleCoverLetter; // 샘플 자기소개서 목록
    private List<String> questions; // 질문 목록
}
