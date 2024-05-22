package com.nhnacademy.student_info.controller;

import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.domain.StudentRegisterRequest;
import com.nhnacademy.student_info.exception.StudentAlreadyExistsException;
import com.nhnacademy.student_info.exception.ValidationFailedException;
import com.nhnacademy.student_info.repository.StudentRepository;
import com.nhnacademy.student_info.validator.StudentRegisterRequestValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {

    private final StudentRepository studentRepository;
    private final StudentRegisterRequestValidator studentRegisterRequestValidator;

    public StudentRegisterController(StudentRepository studentRepository, StudentRegisterRequestValidator studentRegisterRequestValidator) {
        this.studentRepository = studentRepository;
        this.studentRegisterRequestValidator = studentRegisterRequestValidator;
    }

    @ExceptionHandler({StudentAlreadyExistsException.class})
    public String handleRegisterException(Model model, StudentAlreadyExistsException e){
        model.addAttribute("error", e);
        return "error";
    }





    @GetMapping
    public String userRegisterForm() {
        return "studentRegister";
    }

    @PostMapping
    public String registerUser(@ModelAttribute @Valid StudentRegisterRequest studentRegisterRequest,  BindingResult bindingResult,ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        Student student=studentRepository.addStudent(studentRegisterRequest.getId(), studentRegisterRequest.getPassword(), studentRegisterRequest.getName(),
                studentRegisterRequest.getEmail(), studentRegisterRequest.getScore(), studentRegisterRequest.getEvaluation());

        modelMap.addAttribute("student", student);

        return "studentRegister";
    }

    @InitBinder("studentRegisterRequest")
    public void initStudentRegisterRequestBinder(WebDataBinder binder) {
        binder.addValidators(studentRegisterRequestValidator);
    }
}
