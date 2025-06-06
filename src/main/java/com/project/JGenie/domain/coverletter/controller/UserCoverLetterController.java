package com.project.JGenie.domain.coverletter.controller;

import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import com.project.JGenie.domain.coverletter.service.UserCoverLetterService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserCoverLetterController {

    private final UserCoverLetterService userCoverLetterService;

    public UserCoverLetterController(UserCoverLetterService userCoverLetterService) {
        this.userCoverLetterService = userCoverLetterService;
    }

    @GetMapping("/coverletters/user")
    public List<UserCoverLetterEntity> getUserCoverLetters() {
        return userCoverLetterService.getUserCoverLetters();
    }
}
