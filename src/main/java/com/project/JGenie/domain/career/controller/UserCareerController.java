package com.project.JGenie.domain.career.controller;

import com.project.JGenie.domain.career.dto.UserCareerDto;
import com.project.JGenie.domain.career.entity.UserCareerEntity;
import com.project.JGenie.domain.career.service.UserCareerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserCareerController {

    private final UserCareerService userCareerService;

    public UserCareerController(UserCareerService userCareerService) {
        this.userCareerService = userCareerService;
    }


    @PostMapping("/career/register")
    public ResponseEntity<String> registerUserCareer(@RequestBody UserCareerDto userCareerDto) {
        try {
            userCareerService.registerUserCareer(userCareerDto);
            return ResponseEntity.ok("경력 정보가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            log.error("error: " + e);
            return ResponseEntity.badRequest().body("요청에 오류가 발생했습니다. 다시 시도하세요.");
        }
    }

    @GetMapping("/careers")
    public ResponseEntity<List<UserCareerEntity>> getUserCareers() {
        return ResponseEntity.ok(userCareerService.getUserCareers());
    }

    @DeleteMapping("/career/{id}")
    public ResponseEntity<String> deleteUserCareer(@PathVariable Long id) {
        try {
            userCareerService.deleteUserCaree(id);
            return ResponseEntity.ok("경력 정보가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            log.error("error: " + e);
            return ResponseEntity.badRequest().body("요청에 오류가 발생했습니다. 다시 시도하세요.");
        }
    }

}
