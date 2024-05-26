package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.Client;
import com.nhnacademy.nhn_mart.domain.ClientInquiry;
import com.nhnacademy.nhn_mart.exception.SearchInquiryNotFoundException;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.service.ClientServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ClientInquirySearchController {
    private final ClientService clientService;

    public ClientInquirySearchController(ClientServiceImpl clientServiceImpl) {
        this.clientService = clientServiceImpl;
    }
    @ExceptionHandler({SearchInquiryNotFoundException.class})
    public String notFoundInquiryException(Model model) {

        model.addAttribute("error", "분류검색시 고객님의 문의사항이 존재하지않습니다.");
        return "clientError";
    }

    @GetMapping("/client/inquiry/search")
    public String getMain(@RequestParam("id") String id,
                          @RequestParam("category") String category,Model model) {
        Client client = clientService.getClientById(id);

        List<ClientInquiry> clientInquiries = clientService.getInquiriesByCategoryAndClientId(category, id);
        if(clientInquiries ==null){
            throw new SearchInquiryNotFoundException();
        }

        model.addAttribute("client", client);
        model.addAttribute("clientInquiry", clientInquiries);

        return "clientMain";
    }



}
