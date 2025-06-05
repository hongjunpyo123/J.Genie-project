package com.project.JGenie.domain.user.controller;

import com.project.JGenie.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IsValidController {

    private final UserService userService;

    public IsValidController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/valid/{id}")
    public ResponseEntity<String> isValidId(@PathVariable String id) {
        if(userService.isValidId(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("아이디가 이미 존재합니다.");
        } else {
            return ResponseEntity.ok("아이디가 유효합니다.");
        }

    }
}
