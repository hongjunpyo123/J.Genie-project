package com.project.JGenie.domain.coverletter.service;

import com.project.JGenie.domain.career.repository.UserCareerRepository;
import com.project.JGenie.domain.coverletter.dto.request.UserCoverLetterDto;
import com.project.JGenie.domain.coverletter.entity.UserCoverLetterEntity;
import com.project.JGenie.domain.coverletter.repository.UserCoverLetterRepository;
import com.project.JGenie.global.common.util.SecurityUtil;
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
    private final SecurityUtil securityUtil;

    public UserCoverLetterService(UserCoverLetterRepository userCoverLetterRepository, HttpSession session, SecurityUtil securityUtil) {
        this.userCoverLetterRepository = userCoverLetterRepository;
        this.session = session;
        this.securityUtil = securityUtil;
    }


    public void registerUserCoverLetter(UserCoverLetterDto userCoverLetterDto) throws RuntimeException {
        userCoverLetterDto.setUserCoverLetterContent(securityUtil.encrypt(userCoverLetterDto.getUserCoverLetterContent()));
        userCoverLetterRepository.save(userCoverLetterDto.toEntity());
    }

    public List<UserCoverLetterEntity> getUserCoverLetters() {
        List<UserCoverLetterEntity> userCoverLetters = userCoverLetterRepository.findByUserId(session.getAttribute("id").toString());
        for (UserCoverLetterEntity coverLetter : userCoverLetters) {
            coverLetter.setUserCoverLetterContent(securityUtil.decrypt(coverLetter.getUserCoverLetterContent()));
        }
        return userCoverLetters;
    }

    @Transactional
    public void deleteUserCoverLetter(Long id) {
        if (!userCoverLetterRepository.existsById(id) || session.getAttribute("id") == null) {
            throw new RuntimeException("잘못된 요청입니다");
        } else {
            userCoverLetterRepository.deleteById(id);
        }
    }
}
