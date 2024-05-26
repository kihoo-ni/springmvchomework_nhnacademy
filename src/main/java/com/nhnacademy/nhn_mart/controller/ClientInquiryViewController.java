package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.Client;
import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.domain.CsManagerAnswer;
import com.nhnacademy.nhn_mart.exception.ClientInquiryNotFoundException;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.service.ClientServiceImpl;
import com.nhnacademy.nhn_mart.service.CsManagerService;
import com.nhnacademy.nhn_mart.service.CsManagerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@Controller
public class ClientInquiryViewController {
    private final ClientService clientService;
    private final CsManagerService csManagerService;
    public ClientInquiryViewController(ClientServiceImpl clientServiceImpl, CsManagerServiceImpl csManagerService) {
        this.clientService = clientServiceImpl;
        this.csManagerService = csManagerService;
    }


    @ExceptionHandler({ClientInquiryNotFoundException.class})
    public String clientInquiryNotFoundException(Model model) {
        model.addAttribute("error", "문의정보를 찾을 수 없습니다.");
        return "clientError";
    }





    @GetMapping("/client/inquiry/view/{id}/{inquiryNum}")
    public String getClientInquiryView(@PathVariable("id") String id,
                                       @PathVariable("inquiryNum") Long inquiryNum, Model model) {
        log.warn("client ID: "+id);
        Client client=clientService.getClientById(id);

        log.warn("view | Client id"+client.getId()+"Client name"+client.getName());
        ClientInquiry inquiry=clientService.getInquiryById(inquiryNum);
        if(inquiry==null) {
            throw new ClientInquiryNotFoundException();
        }
        log.warn("view | inquiryNum"+inquiry.getInquiryNum()+"id: "+inquiry.getClientId()+"title: "+inquiry.getTitle()+"category: "+inquiry.getCategory()+"content: "+inquiry.getContent()+"imageFiles: "+inquiry.getImageFiles());
        CsManagerAnswer csManagerAnswer=csManagerService.finaAnserByInquiryNum(inquiryNum);
        if(csManagerAnswer!=null) {
            model.addAttribute("csManagerAnswer", csManagerAnswer);
        }
        model.addAttribute("client", client);
        model.addAttribute("inquiry", inquiry);

        return "clientInquiryView";


    }


}
