package com.project.JGenie.domain.coverletter.repository;

import com.project.JGenie.domain.coverletter.entity.ExampleCoverLetterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExampleCoverLetterRepository extends JpaRepository<ExampleCoverLetterEntity, Long> {
   List<ExampleCoverLetterEntity> findByCompanyId(Long companyId); // 회사 ID로 예시 커버레터 조회
}
