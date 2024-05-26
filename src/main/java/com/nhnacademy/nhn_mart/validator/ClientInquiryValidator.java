package com.nhnacademy.nhn_mart.validator;

import com.nhnacademy.nhn_mart.parameter.ClientInquiryRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClientInquiryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ClientInquiryRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientInquiryRequest request = (ClientInquiryRequest) target;

        // title 및 content 유효성 검사
        if (request.getTitle() != null && (request.getTitle().length() < 2 || request.getTitle().length() > 200)) {
            errors.rejectValue("title", "title.length error", "제목은 2자 이상 200자 이하여야 합니다.");
        }

        if (request.getContent() != null && request.getContent().length() > 40000) {
            errors.rejectValue("content", "content.length error", "내용은 최대 40,000자까지 가능합니다.");
        }

        // 첨부파일 개수 유효성 검사
        if (request.getImageFiles() != null && request.getImageFiles().size() > 9) {
            errors.rejectValue("imageFiles", "imageFiles.size error", "첨부파일은 9개까지 가능합니다.");
        }
    }
}