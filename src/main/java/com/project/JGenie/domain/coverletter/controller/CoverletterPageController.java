package com.project.JGenie.domain.coverletter.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoverletterPageController {

    private final HttpSession session;

    public CoverletterPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/coverletter")
    public String coverletterPage() {
        if (session.getAttribute("id") == null) {
            return "err";
        }
        return "user_cover_letter";
    }
}
