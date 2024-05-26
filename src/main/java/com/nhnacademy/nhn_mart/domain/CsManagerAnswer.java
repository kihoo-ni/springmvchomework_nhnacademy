package com.nhnacademy.nhn_mart.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CsManagerAnswer {
    private Long inquiryNum;
    private String content;
    private LocalDateTime answeredDate;
    private String csManagerName;


    public CsManagerAnswer(Long inquiryNum, String content,String csManagerName) {
        this.inquiryNum = inquiryNum;
        this.content = content;
        this.answeredDate = LocalDateTime.now();
        this.csManagerName=csManagerName;
    }


}
