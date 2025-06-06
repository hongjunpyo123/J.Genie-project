package com.project.JGenie.domain.coverletter.controller;

import com.project.JGenie.domain.coverletter.dto.request.CoverLetterAiRequest;
import com.project.JGenie.domain.coverletter.service.CoverLetterAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CoverLetterAiController {

    private final CoverLetterAiService coverLetterAiService;

    public CoverLetterAiController(CoverLetterAiService coverLetterAiService) {
        this.coverLetterAiService = coverLetterAiService;
    }

    @PostMapping("/coverletter/ai")
    public ResponseEntity<String> coverLetterAi(@RequestBody CoverLetterAiRequest coverLetterAiRequest) {
        try {
           coverLetterAiService.coverLetterAi(coverLetterAiRequest);
            return ResponseEntity.ok("성공적으로 생성되었습니다.");
        } catch (Exception e) {
            log.error("error: ", e);
            return ResponseEntity.badRequest().body("요청에 오류가 발생했습니다. 다시 시도하세요.");
        }

    }
}
