package com.project.JGenie.domain.coverletter.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoverletterPageController {

    @GetMapping("/coverletter")
    public String coverletterPage() {
        return "user_cover_letter";
    }
}
