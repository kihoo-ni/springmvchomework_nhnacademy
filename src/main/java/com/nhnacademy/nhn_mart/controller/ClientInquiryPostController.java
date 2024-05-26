package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.domain.ImageFiles;
import com.nhnacademy.nhn_mart.exception.ClientValidationFailedException;
import com.nhnacademy.nhn_mart.exception.FileUploadFailException;
import com.nhnacademy.nhn_mart.parameter.ClientInquiryRequest;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.validator.ClientInquiryValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ClientInquiryPostController {
    private final ClientService clientService;
    private static final String UPLOAD_DIR = System.getProperty("user.home") + File.separator + "downloads" + File.separator;

    private final ClientInquiryValidator validator;



    public ClientInquiryPostController(ClientService clientService, ClientInquiryValidator validator) {
        this.clientService = clientService;
        this.validator = validator;
    }
    @ExceptionHandler({ClientValidationFailedException.class})
    public String validationException(ClientValidationFailedException ex, Model model) {

        model.addAttribute("error", ex);
        return "clientValidationError";
    }

    @ExceptionHandler({FileUploadFailException.class})
    public String fileUploadException(Model model) {

        model.addAttribute("error", "파일 업로드에 실패했습니다.");
        return "clientError";
    }

    @GetMapping("/client/inquiry/post")
    public String getClientInquiries( @SessionAttribute(value = "id") String sessionValue, Model model) {
        model.addAttribute("id", sessionValue);
        return "clientInquiryPost";
    }

    @PostMapping("/client/inquiry/post")
    public String createInquiry( @Valid @ModelAttribute("clientInquiryRequest") ClientInquiryRequest request,
                                 BindingResult bindingResult,
                                 @SessionAttribute(value = "id") String sessionValue) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new ClientValidationFailedException(bindingResult);
        }

        // 허용된 MIME 타입 목록
        List<String> allowedMimeTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/jpg");

        List<ImageFiles> imageFiles = request.getImageFiles().stream()
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    try { //file.getContentType(); ??
                        String mimeType = Files.probeContentType(Paths.get(Objects.requireNonNull(file.getOriginalFilename())));

                        if (!allowedMimeTypes.contains(mimeType)) {
                            throw new FileUploadFailException();
                        }


                        String fileName = file.getOriginalFilename();
                        UUID uuid = UUID.randomUUID();
                        fileName = uuid + "_" + fileName;

                        Path filePath = Paths.get(UPLOAD_DIR + fileName);
                        Files.copy(file.getInputStream(), filePath);
                        return new ImageFiles(fileName, filePath.toString());
                    } catch (IOException e) {
                        throw new FileUploadFailException();
                    }
                })
                .collect(Collectors.toList());

        ClientInquiry inquiry = clientService.createInquiry(request.getTitle(), request.getCategory(), request.getContent(), sessionValue, imageFiles);
        log.warn("id: "+inquiry.getClientId()+"title: "+inquiry.getTitle()+"category: "+inquiry.getCategory()+"content: "+inquiry.getContent()+"imageFiles: "+inquiry.getImageFiles()+"sessionValue: "+sessionValue);
        return "redirect:/client/inquiry/view/"+sessionValue+"/" + inquiry.getInquiryNum();
    }

    @InitBinder("clientInquiryRequest")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
}

//
//@PostMapping("/client/inquiry/post")
//public String createInquiry(@RequestParam("title") String title,
//                            @RequestParam("category") String category,
//                            @RequestParam("content") String content,
//                            @RequestParam(value = "imageFiles", required = false) List<MultipartFile> files,
//                            @SessionAttribute(value = "id") String sessionValue,
//                            Model model) throws IOException {
//    if (files.size() > 9) {
//        model.addAttribute("error", "첨부파일은 9개까지 가능합니다.");
//        return "error";
//    }
//
//    // 허용된 MIME 타입 목록
//    List<String> allowedMimeTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/jpg");
//
//    List<ImageFiles> imageFiles = files.stream()
//            .filter(file -> !file.isEmpty())
//            .map(file -> {
//                try { //file.getContentType(); ??
//                    String mimeType = Files.probeContentType(Paths.get(Objects.requireNonNull(file.getOriginalFilename())));
//
//                    if (!allowedMimeTypes.contains(mimeType)) {
//                        throw new FileUploadFailException();
//                    }
//
//
//                    String fileName = file.getOriginalFilename();
//                    UUID uuid = UUID.randomUUID();
//                    fileName = uuid + "_" + fileName;
//
//                    Path filePath = Paths.get(UPLOAD_DIR + fileName);
//                    Files.copy(file.getInputStream(), filePath);
//                    return new ImageFiles(fileName, filePath.toString());
//                } catch (IOException e) {
//                    throw new FileUploadFailException();
//                }
//            })
//            .collect(Collectors.toList());
//
//    ClientInquiry inquiry = clientService.createInquiry(title, category, content, sessionValue, imageFiles);
//    log.warn("id: "+inquiry.getClientId()+"title: "+inquiry.getTitle()+"category: "+inquiry.getCategory()+"content: "+inquiry.getContent()+"imageFiles: "+inquiry.getImageFiles()+"sessionValue: "+sessionValue);
//    return "redirect:/client/inquiry/view/"+sessionValue+"/" + inquiry.getInquiryNum();
//}