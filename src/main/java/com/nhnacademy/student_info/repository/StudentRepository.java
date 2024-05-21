package com.nhnacademy.student_info.repository;

import com.nhnacademy.student_info.domain.Student;

import java.util.List;

public interface StudentRepository {
    boolean exists(String id);
    boolean matches(String id, String password);
    List<Student> getStudents();
    Student getStudent(String id);
    void addStudent(Student student);
    Student modifyStudent(String session, String id, String password, String name, String email, int score, String evaluation);
    Student addStudent(String id, String password, String name, String email, int score, String evaluation);



}
