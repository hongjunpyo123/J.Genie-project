package com.project.JGenie.domain.user.controller;

import com.project.JGenie.domain.user.dto.UserDto;
import com.project.JGenie.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@RequestBody UserDto userDto) {
        try {
            userService.userSignUp(userDto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (Exception e) {
            log.info("회원가입 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("회원가입 실패");
        }
    }


}
