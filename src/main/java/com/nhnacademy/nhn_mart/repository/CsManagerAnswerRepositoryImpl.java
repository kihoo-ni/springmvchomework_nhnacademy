package com.nhnacademy.nhn_mart.repository;


import com.nhnacademy.nhn_mart.domain.CsManagerAnswer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CsManagerAnswerRepositoryImpl implements CsManagerAnswerRepository {

    private List<CsManagerAnswer> answers = new ArrayList<>();

    // view inquiryNum으로 answer가져오기
    // post에서 inquiryNum, content 로 저장하기


    @Override
    public CsManagerAnswer findAllByInquiryNum(Long inquiryNumber) {
        return answers.stream()
                .filter(inquiry -> inquiry.getInquiryNum().equals(inquiryNumber))
                .findFirst().orElse(null);

    }


    @Override
    public CsManagerAnswer save(CsManagerAnswer csManagerAnswer) {

        answers.add(csManagerAnswer);
        return csManagerAnswer;
    }


}
