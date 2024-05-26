package com.nhnacademy.nhn_mart.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImageFiles {
    private Long imageFileNum;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadDate;
    private Long inquiryNum;

    public ImageFiles( String fileName,  String filePath) {
        this.fileName = fileName;
        this.uploadDate = LocalDateTime.now();
        this.filePath = filePath;

    }


}
