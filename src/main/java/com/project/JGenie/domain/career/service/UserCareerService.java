package com.project.JGenie.domain.career.service;

import com.project.JGenie.domain.career.dto.UserCareerDto;
import com.project.JGenie.domain.career.dto.UserCareerEvaluationDto;
import com.project.JGenie.domain.career.entity.UserCareerEntity;
import com.project.JGenie.domain.career.entity.UserCareerEvaluationEntity;
import com.project.JGenie.domain.career.repository.UserCareerEvaluationRepository;
import com.project.JGenie.domain.career.repository.UserCareerRepository;
import com.project.JGenie.global.common.util.SecurityUtil;
import com.project.JGenie.global.infra.claude.ClaudeAiClient;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCareerService {

    private final UserCareerRepository userCareerRepository;
    private final UserCareerEvaluationRepository userCareerEvaluationRepository;
    private final HttpSession session;
    private final SecurityUtil securityUtil;
    private final ClaudeAiClient claudeAiClient;

    public UserCareerService(UserCareerRepository userCareerRepository, HttpSession session, SecurityUtil securityUtil
    , ClaudeAiClient claudeAiClient, UserCareerEvaluationRepository userCareerEvaluationRepository) {
        this.userCareerRepository = userCareerRepository;
        this.session = session;
        this.securityUtil = securityUtil;
        this.claudeAiClient = claudeAiClient;
        this.userCareerEvaluationRepository = userCareerEvaluationRepository;
    }

    public void registerUserCareer(UserCareerDto userCareerDto) throws RuntimeException {
        String userId = session.getAttribute("id").toString();
        if(userId == null || userId.isEmpty()) {
            throw new RuntimeException("로그인 되어있지 않음");
        }
        userCareerRepository.save(UserCareerEntity.builder()
                .careerTitle(securityUtil.encrypt(userCareerDto.getCareerTitle()))
                .careerType(securityUtil.encrypt(userCareerDto.getCareerType()))
                .careerContent(securityUtil.encrypt(userCareerDto.getCareerContent()))
                .userId(userId)
                .build());
    }

    public List<UserCareerEntity> getUserCareers() {
        List<UserCareerEntity> userCareers = userCareerRepository.findByUserId(session.getAttribute("id").toString());
        List<UserCareerEntity> copyUserCareers = new ArrayList<>();

        for (UserCareerEntity career : userCareers) {
            UserCareerEntity newCareer = new UserCareerEntity();
            newCareer.setCareerId(career.getCareerId());
            newCareer.setUserId(career.getUserId());
            newCareer.setCareerTitle(career.getCareerTitle());
            newCareer.setCareerContent(career.getCareerContent());
            newCareer.setCareerType(career.getCareerType());
            copyUserCareers.add(newCareer);
        }

        for (UserCareerEntity career : copyUserCareers) {
            career.setCareerTitle(securityUtil.decrypt(career.getCareerTitle()));
            career.setCareerContent(securityUtil.decrypt(career.getCareerContent()));
            career.setCareerType(securityUtil.decrypt(career.getCareerType()));
        }
        return copyUserCareers;
    }

    @Transactional
    public void deleteUserCaree(Long id) {
        UserCareerEntity userCareer = userCareerRepository.findById(id).orElse(null);

        if(!session.getAttribute("id").toString().equals(userCareer.getUserId())) {
            throw new RuntimeException("자신의 경력 정보만 삭제할 수 있습니다.");
        }
            userCareerRepository.deleteById(id);
    }

    @Transactional
    public UserCareerEvaluationDto getUserCareerEvaluation() {
        UserCareerEvaluationDto userCareerEvaluationDto = UserCareerEvaluationDto.builder().build();
        String userId = session.getAttribute("id").toString();
        if (userId == null || userId.isEmpty()) {
            throw new RuntimeException("로그인 되어있지 않음");
        }

        List<UserCareerEntity> userCareers = userCareerRepository.findByUserId(userId);
        List<UserCareerEntity> copyUserCareers = new ArrayList<>();
        for (UserCareerEntity career : userCareers) {
            copyUserCareers.add(UserCareerEntity.builder()
                    .careerId(career.getCareerId())
                    .careerTitle(securityUtil.decrypt(career.getCareerTitle()))
                    .careerContent(securityUtil.decrypt(career.getCareerContent()))
                    .careerType(securityUtil.decrypt(career.getCareerType()))
                    .userId(career.getUserId())
                    .build());
        }

        if (userCareers.size() <= 2) {
            userCareerEvaluationDto.setEvaluationScore(0);
            userCareerEvaluationDto.setUserId(userId);
            userCareerEvaluationDto.setEvaluationMessage("커리어가 부족하여 평가할 수 없습니다! 커리어를 추가해주세요.");
            return userCareerEvaluationDto;
        }

        userCareerEvaluationDto = claudeAiClient.AIgenerateCareerEvaluation(copyUserCareers);

        //기존 평가가 존재할 경우 삭제
        if(userCareerEvaluationRepository.existsByUserId(userId)) {
            userCareerEvaluationRepository.deleteByUserId(userId);
        }
        //새로운 평가 저장
        userCareerEvaluationRepository.save(userCareerEvaluationDto.toEntity());
        return userCareerEvaluationDto;

    }

    public UserCareerEvaluationEntity findUserCareerEvaluationByUserId() {
        if(session.getAttribute("id") == null) {
            throw new RuntimeException("로그인 되어있지 않음");
        }
        return userCareerEvaluationRepository.findByUserId(session.getAttribute("id").toString());
    }

    public List<UserCareerEvaluationEntity> findAllUserCareerEvaluationByScore() {
        if(session.getAttribute("id") == null) {
            throw new RuntimeException("로그인 되어있지 않음");
        }
        return userCareerEvaluationRepository.findAllByOrderByEvaluationScoreDesc();
    }
}
