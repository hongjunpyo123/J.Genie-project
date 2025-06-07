package com.project.JGenie.domain.company.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CompanySearchPageController {

    private final HttpSession session;

    public CompanySearchPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/main/search")
    public String companySearchPage() {
        if (session.getAttribute("id") == null) {
            log.error("비로그인 상태입니다.");
            return "err";
        }
        return "main_search";
    }


}
