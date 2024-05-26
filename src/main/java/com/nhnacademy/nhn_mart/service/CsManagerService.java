package com.nhnacademy.nhn_mart.service;

import com.nhnacademy.nhn_mart.domain.CsManager;
import com.nhnacademy.nhn_mart.domain.CsManagerAnswer;

public interface CsManagerService {
    public CsManager getCsManagerById(String csManagerId);



    public boolean matches(String id, String password);

    CsManagerAnswer finaAnserByInquiryNum(Long inquiryNumber);

    CsManagerAnswer saveAnswer(CsManagerAnswer csManagerAnswer);
}
