package com.nhnacademy.nhn_mart.parameter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ClientInquiryRequest {

    private String title;

    private String category;

    private String content;

    private List<MultipartFile> imageFiles;
}
