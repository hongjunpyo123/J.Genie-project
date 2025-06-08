package com.project.JGenie.domain.career.dto;

import com.project.JGenie.domain.career.entity.UserCareerEntity;
import com.project.JGenie.domain.career.entity.UserCareerEvaluationEntity;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCareerEvaluationDto {
    private String userId;
    private String evaluationMessage;
    private int evaluationScore;

    public UserCareerEvaluationEntity toEntity() {
        return UserCareerEvaluationEntity.builder()
                .userId(this.userId)
                .evaluationMessage(this.evaluationMessage)
                .evaluationScore(this.evaluationScore)
                .build();
    }
}
