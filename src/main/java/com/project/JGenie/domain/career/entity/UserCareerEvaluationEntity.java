package com.project.JGenie.domain.career.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "커리어_평가")
public class UserCareerEvaluationEntity {
    @Id
    @Column(length = 255)
    private String userId;

    @Column(length = 1000)
    private String evaluationMessage;

    private int evaluationScore;
}
