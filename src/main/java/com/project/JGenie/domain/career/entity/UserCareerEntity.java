package com.project.JGenie.domain.career.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "커리어")
public class UserCareerEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long careerId;

    @Column(length = 255)
    private String userId;

    @Column(length = 255)
    private String careerType;
    //학력
    //경력
    //경험/활동
    //교육/훈련
    //자격/어학
    //수상
    //스킬/역량
    //프로젝트/성과
    //기타

    @Column(length = 255)
    private String careerTitle;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String careerContent;

}
