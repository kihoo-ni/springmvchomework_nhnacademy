package com.nhnacademy.student_info.controller;

import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.exception.StudentNotFoundException;
import com.nhnacademy.student_info.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(StudentLoginController.class)
@ContextConfiguration(classes = {StudentRepository.class})
class StudentLoginControllerTest {

    private MockMvc mockMvc;

    private StudentRepository studentRepository;

    private Cookie cookie;

    @BeforeEach
    void setUp() {
        cookie = new Cookie("SESSION", "1234");
         studentRepository = mock(StudentRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentLoginController(studentRepository))
                .build();
    }




    @Test
    void handleLoginException() throws Exception {
        // given
        when(studentRepository.getStudent(anyString())).thenThrow(new StudentNotFoundException());

        // when & then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }


    @Test
    void getloginWithSessionCookie() throws Exception {
        // given
        Student student=Student.create("1234","1234");
        when(studentRepository.getStudent(anyString())).thenReturn(student);

        // when then
        mockMvc.perform(get("/login").cookie(new Cookie("SESSION", "1234")))
                .andExpect(status().isOk())
                .andExpect(view().name("studentView"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", student));


    }

    @Test
    void getloginWithoutSessionCookie() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }


    @Test
    void postLoginSuccess() throws Exception {


        Student student=Student.create("1234","1234");
        when(studentRepository.getStudent(anyString())).thenReturn(student);
        when(studentRepository.matches("1234", "1234")).thenReturn(true);

        mockMvc.perform(post("/postlogin")
                        .param("id", "1234")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/view/1234"))
                .andExpect(cookie().value("SESSION", "1234"));
    }

    @Test
    void postLoginFailure() throws Exception {
        mockMvc.perform(post("/postlogin")
                        .param("id", "wrongId")
                        .param("password", "wrongPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }



    @Test
    void logout() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        mockMvc.perform(get("/logout")
                        .cookie(cookie))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(cookie().value("SESSION", (String) null));

    }
}