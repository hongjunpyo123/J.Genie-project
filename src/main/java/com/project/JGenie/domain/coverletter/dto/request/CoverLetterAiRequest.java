package com.project.JGenie.domain.coverletter.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoverLetterAiRequest {
    private String companyName;
    private String companyType;
    private String industryType;
    private String question1;
    private String question2;
    private String question3;
    private String talentProfile; //인재상
}
