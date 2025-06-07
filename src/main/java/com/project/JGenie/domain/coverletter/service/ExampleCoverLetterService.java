package com.project.JGenie.domain.coverletter.service;

import com.project.JGenie.domain.coverletter.dto.request.ExampleCoverLetterDto;
import com.project.JGenie.domain.coverletter.entity.ExampleCoverLetterEntity;
import com.project.JGenie.domain.coverletter.repository.ExampleCoverLetterRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExampleCoverLetterService {

    private final ExampleCoverLetterRepository exampleCoverLetterRepository;
    private final HttpSession session;

    public ExampleCoverLetterService(ExampleCoverLetterRepository exampleCoverLetterRepository, HttpSession session) {
        this.exampleCoverLetterRepository = exampleCoverLetterRepository;
        this.session = session;
    }


    public void registerExampleCoverLetter(ExampleCoverLetterDto exampleCoverLetterDto) throws RuntimeException{
        if(session.getAttribute("id") == null || !session.getAttribute("id").equals("0721hjp")) {
            log.error("잘못된 요청입니다");
            throw new RuntimeException("잘못된 요청입니다.");
        }
        exampleCoverLetterRepository.save(exampleCoverLetterDto.toEntity());
    }

}
