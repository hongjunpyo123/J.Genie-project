package com.project.JGenie.domain.coverletter.controller;

import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import com.project.JGenie.domain.coverletter.service.UserCoverLetterService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserCoverLetterController {

    private final UserCoverLetterService userCoverLetterService;

    public UserCoverLetterController(UserCoverLetterService userCoverLetterService, HttpSession session) {
        this.userCoverLetterService = userCoverLetterService;
    }

    @GetMapping("/coverletters/user")
    public List<UserCoverLetterEntity> getUserCoverLetters() {
        return userCoverLetterService.getUserCoverLetters();
    }

    @DeleteMapping("/coverletter/{id}")
    public ResponseEntity<String> deleteUserCoverLetter(@PathVariable Long id) {
        try {
            userCoverLetterService.deleteUserCoverLetter(id);
            return ResponseEntity.ok("커버레터가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            log.error("error: " + e);
            return ResponseEntity.badRequest().body("요청에 오류가 발생했습니다. 다시 시도하세요.");
        }
    }
}
