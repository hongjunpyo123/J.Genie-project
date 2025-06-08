package com.project.JGenie.domain.career.repository;

import com.project.JGenie.domain.career.entity.UserCareerEvaluationEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCareerEvaluationRepository extends JpaRepository<UserCareerEvaluationEntity, String> {

    UserCareerEvaluationEntity findByUserId(String userId);

    boolean existsByUserId(String userId);

    void deleteByUserId(String userId);

    List<UserCareerEvaluationEntity> findAllByOrderByEvaluationScoreDesc();
}
