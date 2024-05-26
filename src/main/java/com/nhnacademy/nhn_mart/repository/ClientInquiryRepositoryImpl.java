package com.nhnacademy.nhn_mart.repository;

import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClientInquiryRepositoryImpl implements ClientInquiryRepository {

    private List<ClientInquiry> inquiries = new ArrayList<>();
    private Long nextId = 1L;



    // 1. 모든 ClientInquiry 조회
    @Override
    public List<ClientInquiry> findAll() {
        return inquiries;
    }

    // 2. 특정 Client의 문의글을 inquiryNum 내림차순으로 조회
    @Override
    public List<ClientInquiry> findAllByClientId(String clientId) {
        return inquiries.stream()
                .filter(inquiry -> inquiry.getClientId().equals(clientId))
                .sorted(Comparator.comparing(ClientInquiry::getInquiryNum).reversed())
                .collect(Collectors.toList());
    }

    // 3. 특정 카테고리의 문의글을 clientId와 일치하는 항목 조회
    @Override
    public List<ClientInquiry> findAllByCategoryAndClientId(String category, String clientId) {
        return inquiries.stream()
                .filter(inquiry -> inquiry.getCategory().equals(category) && inquiry.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    // 4. 새로운 문의글 등록
    @Override
    public ClientInquiry save(ClientInquiry inquiry) {
        if (inquiry.getInquiryNum() == null) {
            inquiry.setInquiryNum(nextId++);
        }
        inquiries.add(inquiry);
        return inquiry;
    }



    // 5. 답변이 없는 모든 문의글 조회
    @Override
    public List<ClientInquiry> findAllUnanswered() {
        return inquiries.stream()
                .filter(inquiry -> !inquiry.isAnswered())
                .collect(Collectors.toList());
    }
    //6. 유저가 자기글 상세보기 할때 보여주는거
    @Override
    public ClientInquiry findById(Long inquiryNum) {
        return inquiries.stream().filter(Num->Num.getInquiryNum().equals(inquiryNum)).findFirst().orElseGet(null);
    }

}
