package com.project.JGenie.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ProfilePageController {
    private final HttpSession session;

    public ProfilePageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/main/profile")
    public String profilePage() {
        if (session.getAttribute("id") == null) {
            log.error("로그인 정보가 없습니다.");
            return "err";
        }
        return "main_profile";
    }
}
