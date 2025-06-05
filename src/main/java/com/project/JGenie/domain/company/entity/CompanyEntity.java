package com.project.JGenie.domain.company.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "회사")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long companyId;

    @Column(length = 255)
    private String companyName;

    @Column(length = 255)
    private String companyType; // 회사 유형 (예: 스타트업, 대기업 등)

    @Column(length = 255)
    private String industryType; // 산업 유형 (예: IT, 제조업 등)
}
