package com.project.JGenie.domain.company.controller;

import com.project.JGenie.domain.company.dto.CompanyDto;
import com.project.JGenie.domain.company.entity.CompanyEntity;
import com.project.JGenie.domain.company.service.CompanyService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyEntity>> findAllCompany() {
        List<CompanyEntity> companies = companyService.findAllCompany();
        return ResponseEntity.ok(companies);
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody CompanyDto companyDto) {
        try {
            companyService.createCompany(companyDto);
            return ResponseEntity.ok("기업 등록 성공");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).body("error");
        }
    }
}
