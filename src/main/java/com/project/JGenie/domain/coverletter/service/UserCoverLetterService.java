package com.project.JGenie.domain.coverletter.service;

import com.project.JGenie.domain.career.repository.UserCareerRepository;
import com.project.JGenie.domain.coverletter.dto.request.UserCoverLetterDto;
import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import com.project.JGenie.domain.coverletter.repository.UserCoverLetterRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserCoverLetterService {

    private final UserCoverLetterRepository userCoverLetterRepository;
    private final HttpSession session;

    public UserCoverLetterService(UserCoverLetterRepository userCoverLetterRepository, HttpSession session) {
        this.userCoverLetterRepository = userCoverLetterRepository;
        this.session = session;
    }


    public void registerUserCoverLetter(UserCoverLetterDto userCoverLetterDto) throws RuntimeException {
        userCoverLetterRepository.save(userCoverLetterDto.toEntity());
    }

    public List<UserCoverLetterEntity> getUserCoverLetters() {
        System.out.println(session.getAttribute("id").toString());
        return userCoverLetterRepository.findByUserId(session.getAttribute("id").toString());
    }

    @Transactional
    public void deleteUserCoverLetter(Long id) {
        if (!userCoverLetterRepository.existsById(id)) {
            throw new RuntimeException("해당 커버레터 정보가 존재하지 않습니다.");
        } else {
            userCoverLetterRepository.deleteById(id);
        }
    }
}
