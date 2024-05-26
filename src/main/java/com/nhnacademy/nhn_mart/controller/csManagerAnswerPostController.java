package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.domain.CsManager;
import com.nhnacademy.nhn_mart.domain.CsManagerAnswer;
import com.nhnacademy.nhn_mart.exception.CsManagerValidationFailedException;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.service.CsManagerService;
import com.nhnacademy.nhn_mart.service.CsManagerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.IOException;

@Slf4j
@Controller
public class csManagerAnswerPostController {
    private final ClientService clientService;
    private final CsManagerService csManagerService;

    public csManagerAnswerPostController(ClientService clientService, CsManagerServiceImpl csManagerService) {
        this.clientService = clientService;
        this.csManagerService = csManagerService;
    }


    @ExceptionHandler({CsManagerValidationFailedException.class})
    public String validationFailedException(Model model) {
        model.addAttribute("error", "글 내용은 1~40,000자까지 작성가능합니다");
        return "csManagerError";
    }

    @PostMapping("/csManager/answer/post")
    public String createAnswer(@RequestParam("inquiryNum") long inquiryNum,
                                @RequestParam("content") String content,
                                @SessionAttribute(value = "cs") String sessionValue,
                                Model model) throws IOException {
        int contentLength=content.length();
        if(contentLength<1 || contentLength>40000){
           throw new CsManagerValidationFailedException();
        }
        log.warn("contentLength is "+contentLength);
        CsManager csManager=csManagerService.getCsManagerById(sessionValue);
        CsManagerAnswer answer =new CsManagerAnswer(inquiryNum, content,csManager.getName());
        CsManagerAnswer saveAnswer= csManagerService.saveAnswer(answer);
        ClientInquiry inquiry =clientService.getInquiryById(inquiryNum);
        inquiry.setAnswered(true);
        log.warn("saveAnser, inquiryNum: "+saveAnswer.getInquiryNum()+" content: "+saveAnswer.getContent());
      return "redirect:/csManager/answer/main/"+sessionValue;
    }
}

