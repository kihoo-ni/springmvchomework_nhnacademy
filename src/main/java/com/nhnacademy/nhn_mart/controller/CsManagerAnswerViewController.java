package com.nhnacademy.nhn_mart.controller;


import com.nhnacademy.nhn_mart.domain.Client;
import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.domain.CsManager;
import com.nhnacademy.nhn_mart.exception.ClientInquiryNotFoundException;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.service.ClientServiceImpl;
import com.nhnacademy.nhn_mart.service.CsManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class CsManagerAnswerViewController {


        private final ClientService clientService;
        private final CsManagerService csManagerService;

        public CsManagerAnswerViewController(ClientServiceImpl clientServiceImpl, CsManagerService csManagerService) {
            this.clientService = clientServiceImpl;
            this.csManagerService = csManagerService;
        }

    @ExceptionHandler({ClientInquiryNotFoundException.class})
    public String inquiryNotFoundException(Model model) {

        model.addAttribute("error", "고객응답 세부사항을 불러 올 수 없습니다.");
        return "csManagerError";
    }

        @GetMapping("/csManager/answer/view/{id}/{inquiryNum}")
        public String getClientInquiryView(@PathVariable("id") String id,
                                           @PathVariable("inquiryNum") Long inquiryNum, Model model,
                                           @SessionAttribute(value = "cs", required = false) String sessionValue) {
            log.warn("client ID: "+id);
            Client client=clientService.getClientById(id);
            log.warn("view | Client id"+client.getId()+"Client name"+client.getName());
            ClientInquiry inquiry=clientService.getInquiryById(inquiryNum);
            if(inquiry==null) {
                throw new ClientInquiryNotFoundException();
            }
            // csManager 이름 세팅해주기
            CsManager csManager=csManagerService.getCsManagerById(sessionValue);


            log.warn("view | inquiryNum"+inquiry.getInquiryNum()+"id: "+inquiry.getClientId()+"title: "+inquiry.getTitle()+"category: "+inquiry.getCategory()+"content: "+inquiry.getContent()+"imageFiles: "+inquiry.getImageFiles());
            model.addAttribute("client", client);
            model.addAttribute("inquiry", inquiry);
            model.addAttribute("csManager", csManager);

            return "csManagerAnswerView";


        }




}
