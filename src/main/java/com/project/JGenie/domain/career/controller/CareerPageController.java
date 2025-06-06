package com.project.JGenie.domain.career.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CareerPageController {

    private final HttpSession session;

    public CareerPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/home/career")
    public String careerPage() {
        if (session.getAttribute("id") == null) {
            return "err";
        }
        return "main_career";
    }

}
