package com.project.JGenie.domain.career.dto;

import com.project.JGenie.domain.career.entity.UserCareerEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCareerDto {
    private Long careerId;
    private String userId;
    private String careerType;
    private String careerTitle;
    private String careerContent;

    public UserCareerEntity toEntity() {
        return UserCareerEntity.builder()
                .careerId(this.careerId)
                .careerTitle(this.userId)
                .careerType(this.careerType)
                .careerTitle(this.careerTitle)
                .careerContent(this.careerContent)
                .build();
    }
}
