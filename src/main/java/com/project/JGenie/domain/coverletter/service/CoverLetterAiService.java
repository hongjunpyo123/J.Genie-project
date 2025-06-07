package com.project.JGenie.domain.coverletter.service;

import com.project.JGenie.domain.career.entity.UserCareerEntity;
import com.project.JGenie.domain.career.repository.UserCareerRepository;
import com.project.JGenie.domain.career.service.UserCareerService;
import com.project.JGenie.domain.company.entity.CompanyEntity;
import com.project.JGenie.domain.company.repository.CompanyRepository;
import com.project.JGenie.domain.coverletter.dto.request.CoverLetterAiRequest;
import com.project.JGenie.domain.coverletter.entity.ExampleCoverLetterEntity;
import com.project.JGenie.domain.coverletter.repository.ExampleCoverLetterRepository;
import com.project.JGenie.domain.coverletter.repository.UserCoverLetterRepository;
import com.project.JGenie.domain.user.entity.UserEntity;
import com.project.JGenie.domain.user.repository.UserRepository;
import com.project.JGenie.global.common.util.SecurityUtil;
import com.project.JGenie.global.infra.claude.ClaudeAiClient;
import com.project.JGenie.global.infra.claude.dto.request.ClaudeAiRequest;
import com.project.JGenie.global.infra.gemini.GeminiClient;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.List;

@Service
@Slf4j
public class CoverLetterAiService {

    private final UserRepository userRepository;
    private final ExampleCoverLetterRepository exampleCoverLetterRepository;
    private final UserCareerRepository userCareerRepository;
    private final UserCoverLetterRepository userCoverLetterRepository;
    private final CompanyRepository companyRepository;
    private final SecurityUtil securityUtil;
    private final ClaudeAiClient claudeAiClient;
    private final GeminiClient geminiClient;
    private final HttpSession session;
    private final UserCareerService userCareerService;

    public CoverLetterAiService(UserRepository userRepository,
                                 ExampleCoverLetterRepository exampleCoverLetterRepository,
                                 UserCareerRepository userCareerRepository,
                                 SecurityUtil securityUtil,
                                 ClaudeAiClient claudeAiClient,
                                GeminiClient geminiClient,
                                UserCoverLetterRepository userCoverLetterRepository,
                                 HttpSession session,
                                 CompanyRepository companyRepository,
                                UserCareerService userCareerService) {
        this.userRepository = userRepository;
        this.exampleCoverLetterRepository = exampleCoverLetterRepository;
        this.userCareerRepository = userCareerRepository;
        this.securityUtil = securityUtil;
        this.claudeAiClient = claudeAiClient;
        this.geminiClient = geminiClient;
        this.userCoverLetterRepository = userCoverLetterRepository;
        this.companyRepository = companyRepository;
        this.session = session;
        this.userCareerService = userCareerService;
    }

    public void coverLetterAi(CoverLetterAiRequest coverLetterAiRequest) throws RuntimeException{

        if(session.getAttribute("id") == null) {
            log.error("잘못된 요청입니다");
            throw new RuntimeException("잘못된 요청입니다.");
        }

        String userId = session.getAttribute("id").toString();

        UserEntity baseUser = userRepository.findById(userId).orElse(null);
        UserEntity copyUser = UserEntity.builder()
                    .id(baseUser.getId())
                            .age(baseUser.getAge())
                                .major(baseUser.getMajor())
                                        .name(baseUser.getName())
                                                .build();


        copyUser.setAge(securityUtil.decrypt(copyUser.getAge()));
        copyUser.setMajor(securityUtil.decrypt(copyUser.getMajor()));
        copyUser.setName(securityUtil.decrypt(copyUser.getName()));

        Long companyId = companyRepository.findByCompanyName(coverLetterAiRequest.getCompanyName()).getCompanyId();
        CompanyEntity company = companyRepository.findById(companyId).orElse(null);
        List<UserCareerEntity> userCareer = userCareerService.getUserCareers();

        List<ExampleCoverLetterEntity> exampleCoverLetter = exampleCoverLetterRepository.findByCompanyId(companyId);

        ClaudeAiRequest claudeAiRequest = ClaudeAiRequest.builder()
                .user(copyUser)
                .company(company)
                .userCareer(userCareer)
                .exampleCoverLetter(exampleCoverLetter)
                .question1(coverLetterAiRequest.getQuestion1())
                .question2(coverLetterAiRequest.getQuestion2())
                .question3(coverLetterAiRequest.getQuestion3())
                .build();

        claudeAiClient.AIgenerateCoverLetter(claudeAiRequest); //클로드 모델
        //geminiClient.AIgenerateCoverLetter(claudeAiRequest); //제미나이 모델

    }
}
