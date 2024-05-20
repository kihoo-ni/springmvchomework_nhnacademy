package com.nhnacademy.student_info.controller;

import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class StudentLoginController {
    private final StudentRepository studentRepository;

    public StudentLoginController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping("/login")
    public String getlogin(@CookieValue(value = "SESSION", required = false) String session,
                           Model model) {
        if (StringUtils.hasText(session)) {
            Student student=studentRepository.getStudent(session);
            model.addAttribute("student", student);
            return "studentView";
        } else {
            return "loginForm";
        }
    }

    @PostMapping("/postlogin")
    public String postLogin(@RequestParam("id") String id,
                          @RequestParam("password") String password,
                          HttpServletResponse response) {
        if (studentRepository.matches(id, password)) {

            Cookie cookie = new Cookie("SESSION", id);
            response.addCookie(cookie);

            return "redirect:/student/view/"+id;
        } else {
            return "redirect:/login";
        }
    }
}
