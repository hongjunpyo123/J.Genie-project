package com.project.JGenie.domain.coverletter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "사용자_ai생성_자소서")
public class UserCoverLetterEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String userId; // 사용자 ID

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String userCoverLetterContent;



}
