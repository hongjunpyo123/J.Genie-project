package com.project.JGenie.domain.coverletter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "자소서예시")
public class ExampleCoverLetterEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long exampleCoverLetterId;

    private Long companyId;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String exampleCoverLetterContent; // 예시 사소서 내용

}
