package com.nhnacademy.nhn_mart.controller;

import com.nhnacademy.nhn_mart.domain.CsManager;
import com.nhnacademy.nhn_mart.exception.CsManagerNotFoundException;
import com.nhnacademy.nhn_mart.service.CsManagerService;
import com.nhnacademy.nhn_mart.service.CsManagerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/csManager")
public class CsManagerLoginAndLogoutController {
    private final CsManagerService csManagerService;

    public CsManagerLoginAndLogoutController(CsManagerServiceImpl csManagerService) {
        this.csManagerService = csManagerService;
    }

    @ExceptionHandler({CsManagerNotFoundException.class})
    public String handleLoginException(Model model) {
        model.addAttribute("error", "매니저를 찾을 수 없습니다.");
        return "csManagerHome";
    }




    @GetMapping("/login")
    public String getlogin(@SessionAttribute(value = "cs", required = false) String sessionValue) {
        CsManager csManger = csManagerService.getCsManagerById(sessionValue);
        if (csManger == null) {
            return "csManagerHome";
        } else {
            return "redirect:/csManager/answer/main/"+sessionValue;
        }


    }

    @PostMapping("/login")
    public String postLogin(@RequestParam("id") String id,
                            @RequestParam("password") String password,
                            HttpServletRequest request
                          ) {
        if (csManagerService.matches(id, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("cs", id);

            return "redirect:/csManager/answer/main/"+id;
        } else {
            throw new CsManagerNotFoundException();
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("cs");
        } else {
            log.warn("session 로그아웃값 : "+session);

        }
        return "redirect:/csManager/login";
    }

}
