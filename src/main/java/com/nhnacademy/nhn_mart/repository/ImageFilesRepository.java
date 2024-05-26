package com.nhnacademy.nhn_mart.repository;

import com.nhnacademy.nhn_mart.domain.ImageFiles;

public interface ImageFilesRepository {

    ImageFiles save(ImageFiles imageFile);

//    List<ImageFiles> findAllByInquiryNum(Long inquiryNum);

    ImageFiles findById(Long imageFileNum);

    boolean fileNameExistsById(String fileName);
}
