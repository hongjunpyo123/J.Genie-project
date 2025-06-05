package com.project.JGenie.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private String id;
    private String name;
    private String age;
    private String major;
}
