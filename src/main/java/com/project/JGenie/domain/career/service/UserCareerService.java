package com.project.JGenie.domain.career.service;

import com.project.JGenie.domain.career.dto.UserCareerDto;
import com.project.JGenie.domain.career.entity.UserCareerEntity;
import com.project.JGenie.domain.career.repository.UserCareerRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCareerService {

    private final UserCareerRepository userCareerRepository;
    private final HttpSession session;

    public UserCareerService(UserCareerRepository userCareerRepository, HttpSession session) {
        this.userCareerRepository = userCareerRepository;
        this.session = session;
    }

    public void registerUserCareer(UserCareerDto userCareerDto) throws RuntimeException {
        String userId = session.getAttribute("id").toString();
        if(userId == null || userId.isEmpty()) {
            throw new RuntimeException("로그인 되어있지 않음");
        }
        userCareerRepository.save(UserCareerEntity.builder()
                .careerTitle(userCareerDto.getCareerTitle())
                .careerType(userCareerDto.getCareerType())
                .careerContent(userCareerDto.getCareerContent())
                .userId(userId)
                .build());
    }

    public List<UserCareerEntity> getUserCareers() {
        return userCareerRepository.findAll();
    }

    @Transactional
    public void deleteUserCaree(Long id) {

        if(!userCareerRepository.existsByCareerId(id) || session.getAttribute("id") == null) {
            throw new RuntimeException("허용되지 않은 요청입니다");
        }

        UserCareerEntity userCareer = userCareerRepository.findById(id).orElse(null);

        if(!session.getAttribute("id").toString().equals(userCareer.getUserId())) {
            throw new RuntimeException("자신의 경력 정보만 삭제할 수 있습니다.");
        }
            userCareerRepository.deleteById(id);
    }
}
