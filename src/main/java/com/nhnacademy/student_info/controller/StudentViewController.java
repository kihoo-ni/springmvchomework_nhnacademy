package com.nhnacademy.student_info.controller;


import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/view")
public class StudentViewController {

    private final StudentRepository studentRepository;

    public StudentViewController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
