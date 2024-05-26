package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.Client;
import com.nhnacademy.nhn_mart.exception.ClientNotFoundException;
import com.nhnacademy.nhn_mart.service.ClientService;
import com.nhnacademy.nhn_mart.service.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
@RequestMapping("/client")
public class ClientLoginAndLogoutController {
    private final ClientService clientService;

    public ClientLoginAndLogoutController(ClientServiceImpl clientServiceImpl) {
        this.clientService = clientServiceImpl;
    }


    @ExceptionHandler({ClientNotFoundException.class})
    public String handleLoginException(Model model) {
        model.addAttribute("error", "회원정보를 찾을 수 없습니다.");
        return "clientHome";
    }

    @GetMapping("/login")
    public String getlogin(@SessionAttribute(value = "id", required = false) String sessionValue) {
        Client client = clientService.getClientById(sessionValue);
        if (client == null) {
            return "clientHome";
        } else {
            return "redirect:/client/inquiry/main/"+sessionValue;
        }


    }

    @PostMapping("/login")
    public String postLogin(@RequestParam("id") String id,
                            @RequestParam("password") String password,
                            HttpServletRequest request
                          ) {
        if (clientService.matches(id, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("id", id);

            return "redirect:/client/inquiry/main/"+id;
        } else {
            throw new ClientNotFoundException();
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("id");
        } else {
            log.warn("session 로그아웃값 : "+session);
            throw new RuntimeException();
        }



        return "redirect:/client/login";
    }

}
