package com.nhnacademy.student_info.controller;


import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.domain.StudentRegisterRequest;
import com.nhnacademy.student_info.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student/modify")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }





    @ModelAttribute("student")
    public Student getStudentPost(@PathVariable(value = "id", required = false)String id){
        return studentRepository.getStudent(id);
    }

    @GetMapping("/{id}")
    public String getModifyForm(@ModelAttribute("student") Student student) {
        return "studentModify";
    }

    @PostMapping("/{id}")
    public ModelAndView postModify(@ModelAttribute StudentRegisterRequest studentRegisterRequest) {
        Student student = studentRepository.modifyStudent(studentRegisterRequest.getId(), studentRegisterRequest.getPassword(), studentRegisterRequest.getName(),
                studentRegisterRequest.getEmail(), studentRegisterRequest.getScore(), studentRegisterRequest.getEvaluation());

//        ModelAndView mav = new ModelAndView("redirect:/student/modify/" + student.getId());
        ModelAndView mav = new ModelAndView("redirect:/student/register");
    mav.addObject("student", student);
// register에 student줬는데 왜... 못받는건지 ㅠㅠ
        // @PathVariable 안해도되는건지.?

        return mav;
    }

}


