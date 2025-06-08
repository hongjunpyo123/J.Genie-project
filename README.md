# 기업별 맞춤 자소서 Ai JGenie!
본 레포지토리는 2025.06.05 ~  2025.06.08 진행한 개인 프로젝트 입니다.
- 배포 도메인 : https://jgenie.site
## Index
  - [프로젝트 소개](#프로젝트-소개)
  - [주요 기능](#주요-기능)
  - [기술 스택](#기술-스택)
  - [실행 방법](#실행-방법)
  - [데모](#데모)
  - [팀원 소개](#팀원-소개)
  - [프로젝트 구조](#프로젝트-구조)
  - [회고](#회고)
---
## 프로젝트 소개
**JGenie**는 Job 과 Genie의 약자로 취업에 관련된 소원을 들어준다는 의미에서 지어진 서비스명 입니다.

JGenie는 다음과 같은 목적에서 시작하였습니다.
- 나의 취업 활동들을 기록해놓으면 활동 데이터를 기반으로 알아서 자소서를 작성해주면 어떨까? 
- 기업마다 특화된 자소서 작성 Ai를 만들어보면 어떨까? (기업 인재상, 합격자소서 패턴 분석 등)
- 최종적으로 '나' 에게 특화된 자소서 작성 Ai가 탄생할 수 있을까? (내가 작성한 글 패턴 분석, 나의 활동 데이터 기반, 기업이 원하는 인재상 어필) => 기업마다 모두 다른 특화된 자소서 생성 
- 본 프로젝트에서 가장 주된 고민은 "Ai를 어느정도까지 개인화 시킬 수 있을까?" 라는 부분이였습니다. 모델을 직접 훈련시키거나 하는 방법은 방대한 양의 데이터가 필요하고 특정 개인에게 특화시킬 수는 있지만,
여러 사람이 사용하는 범용적인 서비스 에서는 제한적이라 판단하였습니다.
따라서 가장 빠르면서도 접근성이 좋은 프롬프트 엔지니어링 방식을 사용하였습니다.


---
## 주요 기능
JGenie는 다음과 같은 주요 기능을 제공합니다.
1. 회원 - 기본적인 회원관련 기능 (회원가입, 로그인, 계정삭제, 수정, 로그아웃)
2. 보안 - 이름, 나이, 전공, 개인 활동 등 민감한 정보들이 많이 들어가는 만큼 사용자 식별을 위한 id를 제외한 모든 필드는 AES256 암호화 알고리즘을 사용해 모두 암호화하여 저장함
3. 기업별 자소서 작성 - 사용자가 원하는 기업을 선택할 수 있으며 이후 답변을 원하는 자소서 지문을 입력하고 생성할 수 있음
4. 커리어 등록 - 자신의 취업 활동 들을 추가하고 Ai에 반영시킬 수 있음. (자격증, 대외활동, 인턴 등)
5. 개인화 - 자소서가 생성될 때 등록된 자신의 커리어와 전공 학력 등을 바탕으로 특화된 자소서를 생성할 수 있음
6. 기업별 특화 - 자소서가 생성될 때 해당 기업에 합격한 사람의 자소서내용과 인재상을 분석하여 자소서에 반영함
7. 커리어 분석 : 현재 등록된 커리어를 바탕으로 Ai에게 분석을 요청할 수 있으며 분석 결과는 1~100점 사이로 평가됨, 개선할 점 등 표시
8. 랭킹 시스템: 커리어 분석을 통해 평가된 점수는 랭킹에 반영되며 점수가 높을 수록 상위에 노출됨


---
## 기술 스택

### 백엔드
- ![Java](https://img.shields.io/badge/Java-17-orange)
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.0-green)
- ![JPA](https://img.shields.io/badge/JPA-Hibernate-brightgreen)
- ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)

### 인프라
- ![Synology NAS]([https://img.shields.io/badge/AWS-EC2-orange](https://www.synology.com/ko-kr))

### 개발 도구
- ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-2024.1-purple)
- ![Postman](https://img.shields.io/badge/Postman-API_Testing-orange)

### 협업 툴
- ![Git](https://img.shields.io/badge/Git-2.36-red)

### 문서화
- ![Swagger](https://img.shields.io/badge/Swagger-API_Docs-green)

  
---
## 실행 방법

### 사전 요구사항
- JDK 17
- Gradle 8.13
- MySQL 8.0
- Spring Boot 3.4.4

### 로컬 환경에서 실행
1. 레포지토리 클론
```bash
git clone https://github.com/hongjunpyo123/J.Genie-project.git
```

2. properties 설정
```bash
spring.application.name=JGenie

#mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/jgenie?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
spring.datasource.username=DB_name
spring.datasource.password=DB_key
spring.jpa.hibernate.ddl-auto=update

#anthropic
spring.ai.anthropic.api-key=Claude_api_key
spring.ai.anthropic.chat.options.model=claude-3-5-haiku-20241022 #사용 모델 설정.. 성능 향상을 원하면 claude-sonnet-4-20250514 모델 사용
spring.ai.anthropic.chat.options.temperature=0.7
spring.ai.anthropic.chat.options.max-tokens=4000

# Gemini #필수 아님
gemini.url=https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent
gemini.key=Gemioni_api_key

#Security
encrypt.key=AES256 암호화 키
```

3. 프로젝트 빌드 및 실행
```bash
./gradlew clean build
java -jar ../*SNAPSHOT.jar
```

---
## 데모

### 스크린샷
![인트로](https://github.com/user-attachments/assets/e86145e6-0882-424b-be08-8dba9092bb97)
![로그인](https://github.com/user-attachments/assets/6bd0d0de-d434-4f54-abb1-c64084e9bd6f)
![온보딩 및 회원가입 1](https://github.com/user-attachments/assets/af12b1ad-2a15-4f3c-9e61-6439a80e2f6a)
![온보딩 및 회원가입 2](https://github.com/user-attachments/assets/1ebb40db-edb0-493c-8f34-cf6f31bef5bc)
![온보딩 및 회원가입 3](https://github.com/user-attachments/assets/78f44a05-848b-4312-b401-207f05bfeee3)
![온보딩 및 회원가입 4](https://github.com/user-attachments/assets/837cfffb-220a-454a-b24f-ab02b24813fb)
![온보딩 및 회원가입 5](https://github.com/user-attachments/assets/9efea223-2017-4050-a2fb-d8be190826ff)
![메인-홈 1](https://github.com/user-attachments/assets/f9b72cda-5a80-4e83-a880-4693847b9df1)
![메인-홈 2](https://github.com/user-attachments/assets/856349b7-fcdb-4ae0-8b08-ae8ee050afe8)
![메인-검색](https://github.com/user-attachments/assets/7a28bb3e-c8bb-477d-8a62-16600b4ecae1)
![메인-커리어](https://github.com/user-attachments/assets/cf8deadf-270c-478a-864a-86f0bc941d6e)
![메인-커리어 2](https://github.com/user-attachments/assets/e2873d98-6829-429c-b2f3-bd6430c780f0)
![메인-프로필](https://github.com/user-attachments/assets/8a9b4e3b-b93c-46f4-97e1-c3d1046fa3b6)
![내 자기소개서](https://github.com/user-attachments/assets/2fc6d6c6-6f85-467c-8cbb-d2e0aff865ef)
![내 자기소개서 2](https://github.com/user-attachments/assets/786315dd-d897-4df9-b50a-4e58a00eb9fb)
![자기소개서 작성 1](https://github.com/user-attachments/assets/8a55fc58-c4c3-4dc3-b062-ce95ff6fefb5)
![자기소개서 작성 1](https://github.com/user-attachments/assets/17f38537-f553-463e-93b4-9989a8a10eba)

---
## 팀원 소개

### Backend Developer
<img src="https://github.com/hongjunpyo123.png" width="100" height="100" style="border-radius: 50%;" />

- GitHub: [@hongjunpyo123](https://github.com/hongjunpyo123)

---
## 프로젝트 구조
```bash
# JGenie 프로젝트 구조

JGenie/
├── README.md
├── HELP.md
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── project/
        │           └── JGenie/
        │               ├── JGenieApplication.java
        │               ├── domain/
        │               │   ├── admin/
        │               │   │   ├── controller/
        │               │   │   ├── dto/
        │               │   │   └── service/
        │               │   ├── career/
        │               │   │   ├── controller/
        │               │   │   │   ├── CareerPageController.java
        │               │   │   │   ├── UserCareerController.java
        │               │   │   │   └── UserCareerEvaluationController.java
        │               │   │   ├── dto/
        │               │   │   │   ├── UserCareerDto.java
        │               │   │   │   └── UserCareerEvaluationDto.java
        │               │   │   ├── entity/
        │               │   │   │   ├── UserCareerEntity.java
        │               │   │   │   └── UserCareerEvaluationEntity.java
        │               │   │   ├── repository/
        │               │   │   │   ├── UserCareerEvaluationRepository.java
        │               │   │   │   └── UserCareerRepository.java
        │               │   │   └── service/
        │               │   │       └── UserCareerService.java
        │               │   ├── company/
        │               │   │   ├── controller/
        │               │   │   │   ├── CompanyController.java
        │               │   │   │   └── CompanySearchPageController.java
        │               │   │   ├── dto/
        │               │   │   │   └── CompanyDto.java
        │               │   │   ├── entity/
        │               │   │   │   └── CompanyEntity.java
        │               │   │   ├── repository/
        │               │   │   │   └── CompanyRepository.java
        │               │   │   └── service/
        │               │   │       └── CompanyService.java
        │               │   ├── coverletter/
        │               │   │   ├── controller/
        │               │   │   │   ├── CoverLetterAiController.java
        │               │   │   │   ├── CoverletterPageController.java
        │               │   │   │   ├── ExampleCoverLetterController.java
        │               │   │   │   └── UserCoverLetterController.java
        │               │   │   ├── dto/
        │               │   │   │   ├── request/
        │               │   │   │   │   ├── CoverLetterAiRequest.java
        │               │   │   │   │   ├── ExampleCoverLetterDto.java
        │               │   │   │   │   └── UserCoverLetterDto.java
        │               │   │   │   └── response/
        │               │   │   ├── entity/
        │               │   │   │   ├── ExampleCoverLetterEntity.java
        │               │   │   │   └── UserCoverLetterEntity.java
        │               │   │   ├── repository/
        │               │   │   │   ├── ExampleCoverLetterRepository.java
        │               │   │   │   └── UserCoverLetterRepository.java
        │               │   │   └── service/
        │               │   │       ├── CoverLetterAiService.java
        │               │   │       ├── ExampleCoverLetterService.java
        │               │   │       └── UserCoverLetterService.java
        │               │   ├── serverstatus/
        │               │   │   └── ServerStatusController.java
        │               │   └── user/
        │               │       ├── controller/
        │               │       │   ├── IntroPageController.java
        │               │       │   ├── IsValidController.java
        │               │       │   ├── LoginController.java
        │               │       │   ├── LoginPageController.java
        │               │       │   ├── LogoutController.java
        │               │       │   ├── MainPageController.java
        │               │       │   ├── ProfilePageController.java
        │               │       │   ├── SignUpController.java
        │               │       │   ├── SignUpPageController.java
        │               │       │   ├── UpdateController.java
        │               │       │   └── UserController.java
        │               │       ├── dto/
        │               │       │   ├── LoginDto.java
        │               │       │   ├── UserDto.java
        │               │       │   └── UserResponseDto.java
        │               │       ├── entity/
        │               │       │   └── UserEntity.java
        │               │       ├── repository/
        │               │       │   └── UserRepository.java
        │               │       └── service/
        │               │           └── UserService.java
        │               └── global/
        │                   ├── common/
        │                   │   └── util/
        │                   │       └── SecurityUtil.java
        │                   ├── config/
        │                   │   ├── RestTemplateConfig.java
        │                   │   └── WebConfig.java
        │                   ├── infra/
        │                   │   ├── claude/
        │                   │   │   ├── ClaudeAiClient.java
        │                   │   │   ├── dto/
        │                   │   │   │   ├── request/
        │                   │   │   │   │   └── ClaudeAiRequest.java
        │                   │   │   │   └── response/
        │                   │   │   │       └── ClaudeAiResponse.java
        │                   │   │   └── prompt/
        │                   │   │       └── ClaudeAiPrompt.java
        │                   │   └── gemini/
        │                   │       ├── GeminiClient.java
        │                   │       ├── dto/
        │                   │       │   ├── GeminiRequest.java
        │                   │       │   └── GeminiResponse.java
        │                   │       └── enums/
        │                   │           └── GeminiModel.java
        │                   └── security/
        │                       ├── config/
        │                       │   └── SecurityConfig.java
        │                       └── filter/
        └── resources/
            ├── application.properties
            ├── application.properties.sample
            ├── static/
            │   └── images/
            │       └── 스크린샷 2025-06-08 오후 9.23.06.png
            └── templates/
                ├── err.mustache
                ├── intro.mustache
                ├── login.mustache
                ├── main_career.mustache
                ├── main_home.mustache
                ├── main_profile.mustache
                ├── main_search.mustache
                ├── signup.mustache
                └── user_cover_letter.mustache
```


---
## 회고 

### 개선할 점
- 악용 가능성이 있다. 유저의 입력값에 서비스의 대부분 기능이 의존하기 때문에 서비스에서 의도한 바와 다르게 동작할 수 있다.
- 프롬프트 엔지니어링을 통해 Ai모델을 제어하기 때문에 응답의 일관성, 신뢰성이 다소 부족하고, 토큰 소모가 심하다.
