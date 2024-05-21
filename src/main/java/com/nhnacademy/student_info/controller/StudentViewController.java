package com.nhnacademy.student_info.controller;


import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.exception.StudentNotFoundException;
import com.nhnacademy.student_info.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student/view")
public class StudentViewController {

    private final StudentRepository studentRepository;

    public StudentViewController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @ExceptionHandler({StudentNotFoundException.class})
    public String handleViewException(Model model, StudentNotFoundException e){
        model.addAttribute("error", e);
        return "error";
    }

    @ModelAttribute("student")
    public Student getStudentPost(@PathVariable("id") String id){

        return studentRepository.getStudent(id);
    }

    @GetMapping("/{id}")
    public String getStudentView(@ModelAttribute("student") Student student) {
        return "studentView";
    }

    @GetMapping(value = "/{id}", params = "hideScore=yes")
    public String getStudentViewHideScore(@ModelAttribute("student") Student student) {
        return "studentViewHideScore";
    }

}
