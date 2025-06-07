package com.project.JGenie.domain.company.repository;

import com.project.JGenie.domain.company.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findByCompanyName(String companyName); // 회사 이름으로 조회
    List<CompanyEntity> findByCompanyNameContaining(String companyName);
    boolean existsByCompanyName(String companyName); // 회사 이름이 존재하는지 확인
}
