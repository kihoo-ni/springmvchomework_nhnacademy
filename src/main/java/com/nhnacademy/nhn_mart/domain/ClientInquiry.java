package com.nhnacademy.nhn_mart.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClientInquiry {

    private Long inquiryNum;

    private String title;

    private String category;

    private String content;
    private LocalDateTime createdDate;
    private String clientId;
    private boolean isAnswered;


    private List<ImageFiles> imageFiles = new ArrayList<>();

    public ClientInquiry(String title, String category, String content, String clientId,  List<ImageFiles> imageFiles) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.clientId = clientId;
        this.isAnswered = false;
        this.imageFiles = imageFiles;
    }


}
