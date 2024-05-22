package com.nhnacademy.student_info.controller;

import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.exception.StudentAlreadyExistsException;
import com.nhnacademy.student_info.exception.ValidationFailedException;
import com.nhnacademy.student_info.repository.StudentRepository;
import com.nhnacademy.student_info.validator.StudentRegisterRequestValidator;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentRegisterController.class)
@ContextConfiguration(classes = {StudentRepository.class, StudentRegisterRequestValidator.class})
class StudentRegisterControllerTest {

    private MockMvc mockMvc;

    private StudentRepository studentRepository;
    private StudentRegisterRequestValidator studentRegisterRequestValidator= new StudentRegisterRequestValidator();


    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);


        mockMvc = MockMvcBuilders.standaloneSetup(new StudentRegisterController(studentRepository, studentRegisterRequestValidator))
                .setValidator(studentRegisterRequestValidator).build();

    }


    @Test
    void handleRegisterException() throws Exception {

        // given
        when(studentRepository.addStudent(anyString(),anyString(),anyString(),anyString(),anyInt(),anyString()
        )).thenThrow(new StudentAlreadyExistsException());

        Throwable throwable = Assertions.assertThrows(StudentAlreadyExistsException.class,()->{
            studentRepository.addStudent(anyString(),anyString(),anyString(),anyString(),anyInt(),anyString());
        });
//
        assertThat(throwable).isInstanceOf(StudentAlreadyExistsException.class);

    }

    @Test
    void userRegisterForm() throws Exception {
        mockMvc.perform(get("/student/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentRegister"));
    }

    @Test
    void registerUser() throws Exception {
        Student student = Student.create("1234", "1234");
        student.setName("이기훈");
        student.setEmail("gudcjf532@naver.com");
        student.setScore(100);
        student.setEvaluation("베스트");
        when(studentRepository.addStudent(anyString(),anyString(),anyString(),anyString(),anyInt(),anyString()
        )).thenReturn(student);

      mockMvc.perform(post("/student/register").param("id", "1234")
                        .param("password", "1234")
                        .param("name", "이기훈")
                        .param("email", "gudcjf532@naver.com")
                      .param("score", "100")
                        .param("evaluation", "베스트"))
              .andExpect(status().isOk())
                .andExpect(model().attribute("student", student))
              .andExpect(view().name("studentRegister"));


    }

    @Test
    void validationExceptionTest() {
        Throwable th = catchThrowable(() ->
                mockMvc.perform(post("/student/register")
                                .param("name", "")
                                .param("email", "")
                                .param("score","")
                                .param("evaluation",""))
                        .andDo(print()));

        assertThat(th).isInstanceOf(ServletException.class).hasCauseInstanceOf(ValidationFailedException.class);
    }
}