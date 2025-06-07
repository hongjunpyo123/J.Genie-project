package com.project.JGenie.domain.career.repository;

import com.project.JGenie.domain.career.entity.UserCareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCareerRepository extends JpaRepository<UserCareerEntity, Long> {
    List<UserCareerEntity> findByUserId(String userId);
    boolean existsByCareerId(Long careerId);
    void deleteByUserId(String userId);
}
