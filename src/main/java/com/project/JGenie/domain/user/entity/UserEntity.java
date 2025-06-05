package com.project.JGenie.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "유저")
public class UserEntity {
    @Id
    @Column(length = 255)
    private String id;

    @Column(length = 255)
    private String password;

    @Column(length = 255)
    private String name;

    @Column(length = 255)
    private String age;

    @Column(length = 255)
    private String major;
}
