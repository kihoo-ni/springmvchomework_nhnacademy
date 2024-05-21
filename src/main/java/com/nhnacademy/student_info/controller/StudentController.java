package com.nhnacademy.student_info.controller;


import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.domain.StudentModifyRequest;
import com.nhnacademy.student_info.exception.StudentNotFoundException;
import com.nhnacademy.student_info.exception.ValidationFailedException;
import com.nhnacademy.student_info.repository.StudentRepository;
import com.nhnacademy.student_info.validator.StudentModifyRequestValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student/modify")
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentModifyRequestValidator  studentModifyRequestValidator;

    public StudentController(StudentRepository studentRepository, StudentModifyRequestValidator studentModifyRequestValidator) {
        this.studentRepository = studentRepository;
        this.studentModifyRequestValidator = studentModifyRequestValidator;
    }

    @ExceptionHandler({StudentNotFoundException.class})
    public String handleModifyException(Model model, StudentNotFoundException e){
        model.addAttribute("error", e);
        return "error";
    }

    @ModelAttribute("student")
    public Student getStudentPost(@PathVariable(value = "id", required = false) String id) {
        return studentRepository.getStudent(id);
    }

    @GetMapping("/{id}")
    public String getModifyForm(@ModelAttribute("student") Student student) {
        return "studentModify";
    }

    @PostMapping("/{id}")
    public ModelAndView postModify(@ModelAttribute @Valid StudentModifyRequest studentModifyRequest, @CookieValue(value = "SESSION", required = false) String session, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        Student student = studentRepository.modifyStudent(session, studentModifyRequest.getId(), studentModifyRequest.getPassword(), studentModifyRequest.getName(),
                studentModifyRequest.getEmail(), studentModifyRequest.getScore(), studentModifyRequest.getEvaluation());

        ModelAndView mav = new ModelAndView("studentRegister");
        mav.addObject("student", student);


        return mav;
    }

    @InitBinder("studentModifyRequest")
    public void initStudentModifyRequestBinder(WebDataBinder binder) {
        binder.addValidators(studentModifyRequestValidator);
    }

}


