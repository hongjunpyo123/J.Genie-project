package com.project.JGenie.domain.user.dto;

import com.project.JGenie.domain.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String id;
    private String password;
    private String name;
    private String age;
    private String major;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(this.id)
                .password(this.password)
                .name(this.name)
                .age(this.age)
                .major(this.major)
                .build();
    }


}
