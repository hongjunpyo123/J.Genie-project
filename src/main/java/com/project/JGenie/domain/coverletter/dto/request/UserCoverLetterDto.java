package com.project.JGenie.domain.coverletter.dto.request;

import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCoverLetterDto {
    private String userId; // 사용자 ID
    private String userCoverLetterContent;

    public UserCoverLetterEntity toEntity() {
        return UserCoverLetterEntity.builder()
                .userId(this.userId)
                .userCoverLetterContent(this.userCoverLetterContent)
                .build();
    }
}
