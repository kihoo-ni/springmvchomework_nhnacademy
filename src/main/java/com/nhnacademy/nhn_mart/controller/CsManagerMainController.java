package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.domain.CsManager;
import com.nhnacademy.nhn_mart.exception.ClientInquiryNotFoundException;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.service.ClientServiceImpl;
import com.nhnacademy.nhn_mart.service.CsManagerService;
import com.nhnacademy.nhn_mart.service.CsManagerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class CsManagerMainController {
    private final CsManagerService csManagerService;
    private final ClientService clientService;

    public CsManagerMainController(CsManagerServiceImpl csManagerService,
                                   ClientServiceImpl clientService) {
        this.csManagerService = csManagerService;
        this.clientService = clientService;
    }


    @ExceptionHandler({ClientInquiryNotFoundException.class})
    public String clientInquiryException(Model model) {

        model.addAttribute("error", "고객문의를 가져올 수 없습니다.");
        return "csManagerError";
    }


    @GetMapping("/csManager/answer/main/{id}")
    public String getMain(@PathVariable("id") String csManagerId, Model model) {
        CsManager csManager = csManagerService.getCsManagerById(csManagerId);

        List<ClientInquiry> clientInquiries = clientService.getAllInquiries();
        if(clientInquiries == null){
            throw new ClientInquiryNotFoundException();
        }
        model.addAttribute("csManager", csManager);
        model.addAttribute("clientInquiries", clientInquiries);

        return "csManagerMain";
    }



}
