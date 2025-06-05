package com.project.JGenie.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroPageController {
    @GetMapping("/")
    public String introPage() {
        return "intro";
    }
}
