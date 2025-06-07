package com.project.JGenie.domain.user.controller;

import com.project.JGenie.domain.career.service.UserCareerService;
import com.project.JGenie.domain.user.entity.UserEntity;
import com.project.JGenie.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUser() {
        try {
            UserEntity user = userService.findByUserId(session.getAttribute("id").toString());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("로그인 정보가 없습니다.");
            return ResponseEntity.badRequest().body(null);
        }
    }
}
