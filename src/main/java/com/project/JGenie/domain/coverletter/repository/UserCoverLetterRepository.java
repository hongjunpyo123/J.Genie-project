package com.project.JGenie.domain.coverletter.repository;

import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCoverLetterRepository extends JpaRepository<UserCoverLetterEntity, Long> {
    List<UserCoverLetterEntity> findByUserId(String userId); // 사용자 ID로 커버레터 조회
    boolean existsById(Long id); // 커버레터 ID로 존재 여부 확인
}
