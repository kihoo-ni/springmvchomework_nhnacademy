package com.nhnacademy.nhn_mart.repository;


import com.nhnacademy.nhn_mart.domain.ImageFiles;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageFilesRepositoryImpl implements ImageFilesRepository {
    private List<ImageFiles> imageFilesList = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public ImageFiles save(ImageFiles imageFile) {
        imageFile.setImageFileNum(nextId++);
        imageFilesList.add(imageFile);
        return imageFile;
    }

//    @Override
//    public List<ImageFiles> findAllByInquiryNum(Long inquiryNum) {
//        return imageFilesList.stream()
//                .filter(imageFile -> imageFile.getInquiryNum().equals(inquiryNum))
//                .collect(Collectors.toList());
//    }

    @Override
    public ImageFiles findById(Long imageFileNum) {
        return imageFilesList.stream().filter(imageFile -> imageFile.getImageFileNum().equals(imageFileNum)).findFirst().get();
    }

    @Override
    public boolean fileNameExistsById(String fileName) {
            return imageFilesList.stream().anyMatch(imageFile -> imageFile.getFileName().equals(fileName));
        }
}