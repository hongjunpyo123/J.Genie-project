package com.project.JGenie.domain.coverletter.dto.request;

import com.project.JGenie.domain.coverletter.entity.ExampleCoverLetterEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExampleCoverLetterDto {
    private Long companyId;
    private String exampleCoverLetterContent; // 예시 사소서 내용

    public ExampleCoverLetterEntity toEntity() {
        return ExampleCoverLetterEntity.builder()
                .companyId(this.companyId)
                .exampleCoverLetterContent(this.exampleCoverLetterContent)
                .build();
    }
}
