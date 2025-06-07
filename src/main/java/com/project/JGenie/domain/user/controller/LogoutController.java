package com.project.JGenie.domain.user.controller;

import com.project.JGenie.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class LogoutController {
    private final HttpSession session;
    private UserService userService;

    public LogoutController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("/logout")
    public ResponseEntity<String> userLogout() {
        try {
            if(session.getAttribute("id") == null) {
                log.error("로그인 정보가 없습니다.");
                return ResponseEntity.badRequest().body("잘못된 요청입니다.");
            } else {
                session.invalidate(); // 세션 무효화
                log.info("로그아웃 성공");
                return ResponseEntity.ok("로그아웃 성공");
            }
        } catch (Exception e) {
            log.error("로그아웃 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("로그아웃 실패");
        }
    }

    @GetMapping("/delete-account")
    public ResponseEntity<String> deleteAccount() {
        try {
            if(session.getAttribute("id") == null) {
                log.error("로그인 정보가 없습니다.");
                return ResponseEntity.badRequest().body("잘못된 요청입니다.");
            } else {
                String userId = session.getAttribute("id").toString();
                userService.deleteAccount(userId); // 사용자 삭제 서비스 호출
                log.info("계정 삭제 성공");
                return ResponseEntity.ok("계정 삭제 성공");
            }
        } catch (Exception e) {
            log.error("계정 삭제 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("계정 삭제 실패");
        }
    }
}
