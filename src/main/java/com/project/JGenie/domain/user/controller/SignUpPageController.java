package com.project.JGenie.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignUpPageController {

    private final HttpSession session;

    public SignUpPageController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/signup")
    public String signUpPage() {
        if (session.getAttribute("serverStatus") == null) {
            return "err";
        }
        return "signup";
    }
}
