package com.project.JGenie.domain.coverletter.controller;

import com.project.JGenie.domain.coverletter.dto.request.ExampleCoverLetterDto;
import com.project.JGenie.domain.coverletter.entity.ExampleCoverLetterEntity;
import com.project.JGenie.domain.coverletter.service.ExampleCoverLetterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ExampleCoverLetterController {

    private final ExampleCoverLetterService exampleCoverLetterService;

    public ExampleCoverLetterController(ExampleCoverLetterService exampleCoverLetterService) {
        this.exampleCoverLetterService = exampleCoverLetterService;
    }

    @PostMapping("/coverletter/example")
    public ResponseEntity<String> registerExampleCoverLetter(@RequestBody ExampleCoverLetterDto exampleCoverLetterDto) {
        try {
            exampleCoverLetterService.registerExampleCoverLetter(exampleCoverLetterDto);
            return ResponseEntity.ok("자소서 예시 등록 성공");
        } catch (Exception e) {
            log.error("error : " + e.getMessage());
            return ResponseEntity.badRequest().body("자소서 예시 등록에 실패하였습니다.");
        }
    }


}
