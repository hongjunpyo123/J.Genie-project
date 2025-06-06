package com.project.JGenie.domain.company.dto;

import com.project.JGenie.domain.company.entity.CompanyEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {
    private String companyName;
    private String companyType; // 회사 유형 (예: 스타트업, 대기업 등)
    private String industryType; // 산업 유형 (예: IT, 제조업 등)
    private String talentProfile; // 인재상
    public CompanyEntity toEntity() {
        return CompanyEntity.builder()
                .companyName(this.companyName)
                .companyType(this.companyType)
                .industryType(this.industryType)
                .talentProfile(this.talentProfile)
                .build();
    }
}
