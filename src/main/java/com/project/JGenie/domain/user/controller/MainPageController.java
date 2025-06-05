package com.project.JGenie.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    private final HttpSession session;

    public MainPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/main/home")
    public String mainPage() {
        if (session.getAttribute("id") == null) {
            return "err";
        }
        return "main_home";
    }

}
