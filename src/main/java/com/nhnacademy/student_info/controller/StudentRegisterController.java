package com.nhnacademy.student_info.controller;

import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.domain.StudentRegisterRequest;
import com.nhnacademy.student_info.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {

    private final StudentRepository studentRepository;

    public StudentRegisterController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String userRegisterForm() {
        return "studentRegister";
    }

    @PostMapping
    public String registerUser(@ModelAttribute StudentRegisterRequest studentRegisterRequest, ModelMap modelMap) {
       Student student=studentRepository.addStudent(studentRegisterRequest.getId(), studentRegisterRequest.getPassword(), studentRegisterRequest.getName(),
                studentRegisterRequest.getEmail(), studentRegisterRequest.getScore(), studentRegisterRequest.getEvaluation());

        modelMap.addAttribute("student", student);

        return "studentRegister";
    }
}
