package com.project.JGenie.domain.company.repository;

import com.project.JGenie.domain.company.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findByCompanyName(String companyName); // 회사 이름으로 조회
    // Define any custom query methods if needed
}
