package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.Client;
import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.exception.ClientInquiryNotFoundException;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.service.ClientServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class ClientInquiryMainController {
    private final ClientService clientService;

    public ClientInquiryMainController(ClientServiceImpl clientServiceImpl) {
        this.clientService = clientServiceImpl;
    }
    @ExceptionHandler({ClientInquiryNotFoundException.class})
    public String inquiryNotFoundException(Model model) {
        model.addAttribute("error", "문의하신 사항목록을 가져올 수 없습니다.");
        return "clientError";
    }


    @GetMapping("/client/inquiry/main/{id}")
    public String getMain(@PathVariable("id") String clientId, Model model) {
        Client client = clientService.getClientById(clientId);
        List<ClientInquiry> clientInquiries = clientService.getInquiriesByClientId(clientId);
        if(clientInquiries==null){
            throw new ClientInquiryNotFoundException();
        }
        model.addAttribute("client", client);
        model.addAttribute("clientInquiry", clientInquiries);

        return "clientMain";
    }



}
