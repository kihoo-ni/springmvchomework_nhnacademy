package com.nhnacademy.nhn_mart.service;

import com.nhnacademy.nhn_mart.domain.CsManager;
import com.nhnacademy.nhn_mart.domain.CsManagerAnswer;
import com.nhnacademy.nhn_mart.repository.CsManagerAnswerRepository;
import com.nhnacademy.nhn_mart.repository.CsManagerAnswerRepositoryImpl;
import com.nhnacademy.nhn_mart.repository.CsManagerRepository;
import com.nhnacademy.nhn_mart.repository.CsManagerRepositoryImpl;
import org.springframework.stereotype.Service;


@Service
public class CsManagerServiceImpl implements CsManagerService {
    private final CsManagerRepository csManagerRepository;
    private final CsManagerAnswerRepository csManagerAnswerRepository;


    public CsManagerServiceImpl(CsManagerRepositoryImpl csManagerRepository, CsManagerAnswerRepositoryImpl csManagerAnswerRepository) {
        this.csManagerRepository = csManagerRepository;
        this.csManagerAnswerRepository = csManagerAnswerRepository;
    }

    @Override
    public CsManager getCsManagerById(String id) {
        return csManagerRepository.getCsManager(id);
    }


    @Override
    public boolean matches(String id, String password) {
        return csManagerRepository.matches(id, password);
    }


    @Override
    public CsManagerAnswer finaAnserByInquiryNum(Long inquiryNumber) {
        return csManagerAnswerRepository.findAllByInquiryNum(inquiryNumber);
    }

    @Override
    public CsManagerAnswer saveAnswer(CsManagerAnswer csManagerAnswer) {
        return csManagerAnswerRepository.save(csManagerAnswer);
    }
}
