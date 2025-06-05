package com.project.JGenie.domain.user.repository;

import com.project.JGenie.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsById(String id);
}
