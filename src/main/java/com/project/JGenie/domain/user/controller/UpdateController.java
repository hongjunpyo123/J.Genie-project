package com.project.JGenie.domain.user.controller;

import com.project.JGenie.domain.user.dto.UserDto;
import com.project.JGenie.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UpdateController {

    private final UserService userService;

    public UpdateController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        try {
            userService.updateUser(userDto);
            return ResponseEntity.ok("사용자 정보 업데이트 성공");
        } catch (Exception e) {
            log.error("사용자 정보 업데이트 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("사용자 정보 업데이트 실패");
        }

    }
}
