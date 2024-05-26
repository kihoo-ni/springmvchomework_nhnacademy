package com.nhnacademy.nhn_mart.repository;

import com.nhnacademy.nhn_mart.domain.ClientInquiry;

import java.util.List;

public interface ClientInquiryRepository {

    public List<ClientInquiry>findAll();
    public List<ClientInquiry> findAllByClientId(String clientId);
    public List<ClientInquiry> findAllByCategoryAndClientId(String category, String clientId);
    public ClientInquiry save(ClientInquiry clientInquiry);
    public List<ClientInquiry>findAllUnanswered();
    ClientInquiry findById(Long inquiryNum);


    }

