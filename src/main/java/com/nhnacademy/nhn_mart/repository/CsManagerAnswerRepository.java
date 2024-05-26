package com.nhnacademy.nhn_mart.repository;

import com.nhnacademy.nhn_mart.domain.CsManagerAnswer;

public interface CsManagerAnswerRepository {
    CsManagerAnswer findAllByInquiryNum(Long inquiryNumber);

    CsManagerAnswer save(CsManagerAnswer csManagerAnswer);
}
