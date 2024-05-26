package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.ImageFiles;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.service.ClientServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ClientInquiryImageFileController {

    private static final String UPLOAD_DIR =System.getProperty("user.home") + File.separator + "downloads" + File.separator;

    private final ClientService clientService;

    public ClientInquiryImageFileController(ClientServiceImpl clientServiceImpl) {
        this.clientService = clientServiceImpl;
    }

    @ExceptionHandler({FileNotFoundException.class})
    public String fileNotFoundException(Model model) {
        model.addAttribute("error", "파일을 찾을 수 업습니다.");
        return "clientError";
    }

    @GetMapping("/client/inquiry/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long imageFileId) throws Exception {
        ImageFiles imageFile = clientService.getImageFileById(imageFileId);
        if(imageFile == null) {
           throw new FileNotFoundException("File not found: " + imageFileId);
        }

        Path filePath = Paths.get(UPLOAD_DIR).resolve(imageFile.getFileName());
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageFile.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping("/client/inquiry/image/{fileId}")
    public ResponseEntity<Resource> getImage(@PathVariable("fileId") Long imageFileId) throws Exception {
        ImageFiles imageFile = clientService.getImageFileById(imageFileId);
        if(imageFile == null) {
            throw new FileNotFoundException("File not found: " + imageFileId);
        }
        Path imagePath = Paths.get(UPLOAD_DIR).resolve(imageFile.getFileName());
        Resource resource = new UrlResource(imagePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageFile.getFileName() + "\"")
                .body(resource);
    }
}
