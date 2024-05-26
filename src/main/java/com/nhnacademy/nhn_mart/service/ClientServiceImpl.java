package com.nhnacademy.nhn_mart.service;

import com.nhnacademy.nhn_mart.domain.Client;
import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.domain.ImageFiles;
import com.nhnacademy.nhn_mart.repository.ClientInquiryRepository;
import com.nhnacademy.nhn_mart.repository.ClientRepository;
import com.nhnacademy.nhn_mart.repository.ImageFilesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientInquiryRepository inquiryRepository;
    private final ClientRepository clientRepository;
    private final ImageFilesRepository imageFilesRepository;

    public ClientServiceImpl(ClientInquiryRepository inquiryRepository,
                             ClientRepository clientRepository,
                             ImageFilesRepository imageFilesRepository) {
        this.inquiryRepository = inquiryRepository;
        this.clientRepository = clientRepository;
        this.imageFilesRepository = imageFilesRepository;
    }
    @Override
    public ClientInquiry createInquiry(String title, String category, String content,String clientId,  List<ImageFiles> imageFiles) {

        ClientInquiry inquiry = new ClientInquiry(title, category, content, clientId, imageFiles);
        inquiryRepository.save(inquiry);
        for (ImageFiles imageFile : imageFiles) {
            imageFile.setInquiryNum(inquiry.getInquiryNum());
            imageFilesRepository.save(imageFile);
        }




        return inquiry;
    }
    @Override
    public List<ClientInquiry> getAllInquiries() {

        return inquiryRepository.findAll();
    }
    @Override
    public List<ClientInquiry> getInquiriesByClientId(String clientId) {
        return inquiryRepository.findAllByClientId(clientId);
    }
    @Override
    public List<ClientInquiry> getInquiriesByCategoryAndClientId(String category, String clientId) {
        return inquiryRepository.findAllByCategoryAndClientId(category, clientId);
    }

    @Override
    public ClientInquiry getInquiryById(Long inquiryNum) {
        return inquiryRepository.findById(inquiryNum);
    }
    @Override
    public Client getClientById(String clientId) {
        return clientRepository.getClient(clientId);
    }



    @Override
    public boolean matches(String id, String password) {
        return clientRepository.matches(id, password);
    }

    @Override
    public ImageFiles getImageFileById(Long imageFileId) {
        return imageFilesRepository.findById(imageFileId);
    }

    @Override
    public boolean fileNameExistsById(String fileName){
        return imageFilesRepository.fileNameExistsById(fileName);
    }
}

