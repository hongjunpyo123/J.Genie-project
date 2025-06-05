package com.project.JGenie.domain.user.controller;

import com.project.JGenie.domain.user.dto.LoginDto;
import com.project.JGenie.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(LoginDto loginDto) {
        try {
            return null;
        } catch (Exception e) {
            log.error("로그인 실패: {}", e.getMessage());
            return null;
        }
    }


}
