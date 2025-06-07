package com.project.JGenie.domain.company.service;

import com.project.JGenie.domain.company.dto.CompanyDto;
import com.project.JGenie.domain.company.entity.CompanyEntity;
import com.project.JGenie.domain.company.repository.CompanyRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final HttpSession session;

    public CompanyService(CompanyRepository companyRepository, HttpSession session) {
        this.companyRepository = companyRepository;
        this.session = session;
    }

    public List<CompanyEntity> findAllCompany() {
        return companyRepository.findAll();
    }

    public void createCompany(CompanyDto companyDto) {
        String accessUserName = session.getAttribute("id").toString();

        if(accessUserName == null || !accessUserName.equals("0721hjp")) {
            throw new RuntimeException("권한부족");
        }

        companyRepository.save(companyDto.toEntity());
    }

    public List<CompanyEntity> findByCompanyNameContaining(String companyName) {
        if(session.getAttribute("id") == null) {
            throw new RuntimeException("허용되지 않은 요청입니다");
        }

        return companyRepository.findByCompanyNameContaining(companyName);
    }
}
