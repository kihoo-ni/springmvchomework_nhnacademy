package com.nhnacademy.student_info.controller;

import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.exception.ValidationFailedException;
import com.nhnacademy.student_info.repository.StudentRepository;
import com.nhnacademy.student_info.validator.StudentModifyRequestValidator;
import jakarta.servlet.ServletException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {StudentRepository.class, StudentModifyRequestValidator.class})
class StudentControllerTest {


    private MockMvc mockMvc;

    private StudentRepository studentRepository;


    @BeforeEach
    void setUp() {

        studentRepository = mock(StudentRepository.class);

        StudentModifyRequestValidator studentModifyRequestValidator = new StudentModifyRequestValidator();


        mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(studentRepository, studentModifyRequestValidator))
                .setValidator(studentModifyRequestValidator).build();
    }

    @Test
    void getModifyFormTest() throws Exception {
        Student student = Student.create("1234", "1234");
        student.setName("이기훈");
        student.setEmail("gudcjf532@naver.com");
        student.setScore(100);
        student.setEvaluation("베스트");
        when(studentRepository.getStudent(anyString())).thenReturn(student);

        mockMvc.perform(get("/student/modify/{id}", "1234"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student", student))
                .andExpect(view().name("studentModify"));
    }

    @Test
    void postModifyTest() throws Exception {


        Student newstudent = Student.create("1234", "1234");
        newstudent.setName("이기훈");
        newstudent.setEmail("gudcjf532@naver.com");
        newstudent.setScore(100);
        newstudent.setEvaluation("베스트");

        Student oldStudent = Student.create("1234", "1234");
        oldStudent.setName("이훈");
        oldStudent.setEmail("gudcjf532@google.com");
        oldStudent.setScore(90);
        oldStudent.setEvaluation("best");

        when(studentRepository.modifyStudent(anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyString()))
                .thenReturn(newstudent);


        Assertions.assertThat(newstudent.getName()).isNotEqualTo(oldStudent.getName());
        Assertions.assertThat(newstudent.getEmail()).isNotEqualTo(oldStudent.getEmail());
        Assertions.assertThat(newstudent.getScore()).isNotEqualTo(oldStudent.getScore());
        Assertions.assertThat(newstudent.getEvaluation()).isNotEqualTo(oldStudent.getEvaluation());
    }


    @Test
    void validationExceptionTest() {
        Student student = Student.create("1234", "1234");
        student.setName("이기훈");
        student.setEmail("gudcjf532@naver.com");
        student.setScore(100);
        student.setEvaluation("베스트");
        when(studentRepository.modifyStudent(anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyString()))
                .thenReturn(student);

        Throwable th = catchThrowable(() ->
                mockMvc.perform(post("/student/modify/{id}", "1234")
                                .param("name", "")
                                .param("email", "")
                                .param("score", "")
                                .param("evaluation", ""))
                        .andDo(print()));

        Assertions.assertThat(th).isInstanceOf(ServletException.class).hasCauseInstanceOf(ValidationFailedException.class);
    }

}
//@Test
//void postModifyTest() throws Exception {
//
//
//    Student newstudent = Student.create("1234", "1234");
//    newstudent.setName("이기훈");
//    newstudent.setEmail("gudcjf532@naver.com");
//    newstudent.setScore(100);
//    newstudent.setEvaluation("베스트");
//
//    Student oldStudent = Student.create("1234", "1234");
//    oldStudent.setName("이훈");
//    oldStudent.setEmail("gudcjf532@google.com");
//    oldStudent.setScore(90);
//    oldStudent.setEvaluation("best");
//
//    when(studentRepository.modifyStudent(anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyString()))
//            .thenReturn(newstudent);
//
//    MvcResult result = mockMvc.perform(post("/student/modify/{id}", "1234")
//                    .param("password", "1234")
//                    .param("name", "이기훈")
//                    .param("email", "gudcjf532@naver.com")
//                    .param("score", "100")
//                    .param("evaluation", "베스트")).andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(view().name("studentRegister"))
//            .andExpect(model().attributeExists("student"))
//            .andReturn();
//
//    Optional<Student> newStudent = Optional.ofNullable(result.getModelAndView())
//            .map(ModelAndView::getModel)
//            .map(m -> m.get("student"))
//            .map(Student.class::cast);
//
//    Assertions.assertThat(newStudent).isPresent();
//    Assertions.assertThat(newStudent.get().getId()).isNotEqualTo(oldStudent.getId());
//    Assertions.assertThat(newStudent.get().getName()).isNotEqualTo(oldStudent.getName());
//    Assertions.assertThat(newStudent.get().getEmail()).isNotEqualTo(oldStudent.getEmail());
//    Assertions.assertThat(newStudent.get().getScore()).isNotEqualTo(oldStudent.getScore());
//    Assertions.assertThat(newStudent.get().getEvaluation()).isNotEqualTo(oldStudent.getEvaluation());
//}
