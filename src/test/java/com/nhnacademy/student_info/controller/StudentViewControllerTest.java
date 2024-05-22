package com.nhnacademy.student_info.controller;

import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentViewController.class)
@ContextConfiguration(classes = {StudentRepository.class})
class StudentViewControllerTest {


    private MockMvc mockMvc;

    private StudentRepository studentRepository;

    private Cookie cookie;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        cookie = new Cookie("SESSION", "1234");

        mockMvc = MockMvcBuilders.standaloneSetup(new StudentViewController(studentRepository)).build();


    }

    @Test
    void getStudentView() throws Exception {


        Student student = Student.create("1234", "1234");
        student.setName("이기훈");
        student.setEmail("gudcjf532@naver.com");
        student.setScore(100);
        student.setEvaluation("베스트");
        when(studentRepository.getStudent(anyString())).thenReturn(student);

        MvcResult mvcResult = mockMvc.perform(get("/student/view/{id}", "1234")
                        .cookie(cookie)
                        .param("hideScore", "no"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentView"))
                .andReturn();

        Optional<Student> newStudent = Optional.ofNullable(mvcResult.getModelAndView())
                .map(ModelAndView::getModel)
                .map(m -> m.get("student"))
                .map(Student.class::cast);

        assertThat(newStudent).isPresent();
        assertThat(newStudent.get().getId()).isEqualTo(student.getId());
        assertThat(newStudent.get().getName()).isEqualTo(student.getName());
        assertThat(newStudent.get().getEmail()).isEqualTo(student.getEmail());
        assertThat(newStudent.get().getScore()).isEqualTo(student.getScore());
        assertThat(newStudent.get().getEvaluation()).isEqualTo(student.getEvaluation());
    }

    @Test
    void getStudentViewHideScore() throws Exception {
        Student student = Student.create("1234", "1234");
        student.setName("이기훈");
        student.setEmail("gudcjf532@naver.com");
        student.setScore(100);
        student.setEvaluation("베스트");
        when(studentRepository.getStudent(anyString())).thenReturn(student);

        MvcResult mvcResult = mockMvc.perform(get("/student/view/{id}", "1234")
                        .cookie(cookie)
                        .param("hideScore", "yes"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentViewHideScore"))
                .andReturn();

        Optional<Student> newStudent = Optional.ofNullable(mvcResult.getModelAndView())
                .map(ModelAndView::getModel)
                .map(m -> m.get("student")) // Corrected spelling of attribute name
                .map(Student.class::cast);

        assertThat(newStudent).isPresent();
        assertThat(newStudent.get().getId()).isEqualTo(student.getId());
        assertThat(newStudent.get().getName()).isEqualTo(student.getName());
        assertThat(newStudent.get().getEmail()).isEqualTo(student.getEmail());
        assertThat(newStudent.get().getScore()).isEqualTo(student.getScore());
        assertThat(newStudent.get().getEvaluation()).isEqualTo(student.getEvaluation());
    }
}