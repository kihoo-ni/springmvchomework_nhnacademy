package com.nhnacademy.nhn_mart.service;

import com.nhnacademy.nhn_mart.domain.Client;
import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.domain.ImageFiles;

import java.util.List;

public interface ClientService {


    public ClientInquiry createInquiry(String title, String category, String content, String clientId,
                                      List<ImageFiles> imageFiles);

    public List<ClientInquiry> getAllInquiries();


    public List<ClientInquiry> getInquiriesByClientId(String clientId);

    public List<ClientInquiry> getInquiriesByCategoryAndClientId(String category, String clientId);


    public ClientInquiry getInquiryById(Long inquiryNum);

    public Client getClientById(String clientId);



    public boolean matches(String id, String password);

    public ImageFiles getImageFileById(Long imageFileId);

    public boolean fileNameExistsById(String fileName);
}
