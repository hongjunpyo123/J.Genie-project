package com.project.JGenie.domain.career.controller;

import com.project.JGenie.domain.career.dto.UserCareerEvaluationDto;
import com.project.JGenie.domain.career.entity.UserCareerEvaluationEntity;
import com.project.JGenie.domain.career.service.UserCareerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/career/evaluation")
public class UserCareerEvaluationController {

    private final UserCareerService userCareerService;
    private final HttpSession session;

    public UserCareerEvaluationController(UserCareerService userCareerService, HttpSession session) {
        this.userCareerService = userCareerService;
        this.session = session;
    }

    @GetMapping
    public ResponseEntity<UserCareerEvaluationDto> getUserCareerEvaluation() {
        try {
            if(session.getAttribute("id") == null) {
                log.error("로그인상태가 아닙니다.");
                return ResponseEntity.badRequest().body(null);
            }
            UserCareerEvaluationDto userCareerEvaluationDto = userCareerService.getUserCareerEvaluation();
            return ResponseEntity.ok(userCareerEvaluationDto);
        } catch (Exception e) {
            log.error("error: ", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findUserCareerEvaluationByUserId() {
        try {
            if(session.getAttribute("id") == null) {
                log.error("로그인상태가 아닙니다.");
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(userCareerService.findUserCareerEvaluationByUserId());
        } catch (Exception e) {
            log.error("error: ", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search/all/score")
    public ResponseEntity<List<UserCareerEvaluationEntity>> findAllUserCareerEvaluationByScore() {
        try {
            if(session.getAttribute("id") == null) {
                log.error("로그인상태가 아닙니다.");
                return ResponseEntity.badRequest().body(null);
            }
            List<UserCareerEvaluationEntity> evaluations = userCareerService.findAllUserCareerEvaluationByScore();
            return ResponseEntity.ok(evaluations);
        } catch (Exception e) {
            log.error("error: ", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

}
