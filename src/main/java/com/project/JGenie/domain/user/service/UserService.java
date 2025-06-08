package com.project.JGenie.domain.user.service;

import com.project.JGenie.domain.career.repository.UserCareerEvaluationRepository;
import com.project.JGenie.domain.career.repository.UserCareerRepository;
import com.project.JGenie.domain.coverletter.repository.UserCoverLetterRepository;
import com.project.JGenie.domain.user.dto.LoginDto;
import com.project.JGenie.domain.user.dto.UserDto;
import com.project.JGenie.domain.user.entity.UserEntity;
import com.project.JGenie.domain.user.repository.UserRepository;
import com.project.JGenie.global.common.util.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserCoverLetterRepository userCoverLetterRepository;
    private final UserCareerRepository userCareerRepository;
    private final UserCareerEvaluationRepository userCareerEvaluationRepository;
    private final SecurityUtil securityUtil;
    private final HttpSession session;

    public UserService(UserRepository userRepository, SecurityUtil securityUtil, HttpSession session
    , UserCoverLetterRepository userCoverLetterRepository, UserCareerRepository userCareerRepository
    , UserCareerEvaluationRepository userCareerEvaluationRepository) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
        this.session = session;
        this.userCoverLetterRepository = userCoverLetterRepository;
        this.userCareerRepository = userCareerRepository;
        this.userCareerEvaluationRepository = userCareerEvaluationRepository;
    }

    public boolean isValidId(String id) {
        if(userRepository.existsById(id)) {
            return true; // 아이디가 존재하는 경우
        } else {
            return false; // 아이디가 존재하지 않는 경우
        }
    }

    public void userSignUp(UserDto userDto) {
        if(userRepository.existsById(userDto.getId())) {
            throw new RuntimeException("아이디가 이미 존재합니다.");
        } else {
            userDto.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt())); //비밀번호 필드 암호화
            /*
            // 로그인 시
            boolean isMatch = BCrypt.checkpw(inputPassword, user.getHashedPassword());
            */
            userDto.setName(securityUtil.encrypt(userDto.getName())); //이름 필드 암호화
            userDto.setAge(securityUtil.encrypt(userDto.getAge())); //나이 필드 암호화
            userDto.setMajor(securityUtil.encrypt(userDto.getMajor())); //전공 필드 암호화

            session.setAttribute("id", userDto.getId()); // 세션에 아이디 저장
            userRepository.save(userDto.toEntity());
        }
    }

    public void userLogin(LoginDto loginDto) {
        if(!userRepository.existsById(loginDto.getId())) {
            throw new RuntimeException("아이디가 존재하지 않습니다.");
        } else {
            UserEntity userEntity = findUser(loginDto.getId());
            if(!BCrypt.checkpw(loginDto.getPassword(), userEntity.getPassword())) {
                throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            } else {
                // 로그인 성공
                session.setAttribute("id", userEntity.getId()); // 세션에 아이디 저장
            }
        }
    }

    public UserEntity findUser(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity findByUserId(String userId) {
            UserEntity user = userRepository.findById(userId).orElse(null);

            return UserEntity.builder()
                    .id(user.getId())
                    .name(securityUtil.decrypt(user.getName()))
                    .age(securityUtil.decrypt(user.getAge()))
                    .major(securityUtil.decrypt(user.getMajor()))
                    .build();
    }

    @Transactional
    public void deleteAccount(String userId) {
        if(!userRepository.existsById(userId)) {
            throw new RuntimeException("아이디가 존재하지 않습니다.");
        } else {
            userCoverLetterRepository.deleteByUserId(userId);
            userCareerRepository.deleteByUserId(userId);
            userRepository.deleteById(userId);
            userCareerEvaluationRepository.deleteByUserId(userId);
            session.invalidate(); // 세션 무효화
        }
    }

    @Transactional
    public void updateUser(UserDto userDto) {
        if(session.getAttribute("id") == null || !session.getAttribute("id").equals(userDto.getId()) || !userRepository.existsById(userDto.getId())) {
            throw new RuntimeException("비정상적인 요청");
        }

        if(!isDigitsOnly(userDto.getAge())) {
            throw new RuntimeException("비정상적인 요청");
        }

        UserEntity userEntity = userRepository.findById(userDto.getId()).orElse(null);

        if(userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            userDto.setPassword(userEntity.getPassword()); // 비밀번호가 입력되지 않은 경우 기존 비밀번호 유지
        } else {
            if(userDto.getPassword().length() < 8) {
                throw new RuntimeException("비밀번호는 8자 이상이어야 합니다.");
            }
            userEntity.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()));
        }

        userEntity.setName(securityUtil.encrypt(userDto.getName()));
        userEntity.setAge(securityUtil.encrypt(userDto.getAge()));
        userEntity.setMajor(securityUtil.encrypt(userDto.getMajor()));

        userRepository.save(userEntity);
    }

    public boolean isDigitsOnly(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}
